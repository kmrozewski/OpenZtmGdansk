# About
The application displays timetables for all public transportation stops and vehicles (buses, trams, trolleys) in the Tricity area (Gdańsk/Sopot/Gdynia).  The application displays estimated vehicle arrival to the stop and eventual delay or acceleration.

Thanks to the site https://gdansk.pl/otwarte-dane and Tristar for the public API this application is using for displaying the current timetables.

**Currently the app is deployed and accessible for everyone and for free at** https://mniejstresudlawero.pl

# Local (dev) deployment
In `ops-local` directory, run the `start-local-env.sh` script


# Cloud deployment
This page shows steps to reproduce in order to deploy the `YOUR_DOMAIN_NAME.pl` app on any Ubuntu 18.04 cloud instance with valid HTTPS certificate
**Turn off the http firewall traffic on the google cloud instance**

## Cloud DNS (optional)
This step is optional since domain provider usually have it’s own DNS that you can configure in the similar manner that’s done below. Use the Cloud DNS if needed.

### Google Cloud (Network services -> Cloud DNS)
1. Create zone.
* *Zone name*: `YOUR_DOMAIN_NAME-zone`
* *DNS name*: `YOUR_DOMAIN_NAME.pl`
2. Add record set - this will add the possibility to access the page via `https://www.YOUR_DOMAIN_NAME.pl` instead of `https://YOUR_DOMAIN_NAME.pl`.
* *DNS Name*: `www.` (there should be `www.YOUR_DOMAIN_NAME.pl`)
* *Resoruce Record Type*: `CNAME`
* *Canonical name*: `YOUR_DOMAIN_NAME.pl`

### AWS Route53 (optional)
The steps mentioned in the Google Cloud DNS are similar.


## Prerequisities
```bash
sudo apt-get update
sudo apt-get install systemd
sudo add-apt-repository ppa:certbot/certbot
sudo apt-get update
sudo apt-get install certbot
sudo apt-get install nginx
sudo apt-get install git-core
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update -y
sudo apt-get install oracle-java8-installer
sudo apt-get install maven
curl -sL https://deb.nodesource.com/setup_10.x -o nodesource_setup.sh
sudo bash nodesource_setup.sh
sudo apt install nodejs
sudo npm install -g gulp
sudo npm install -g bower
```

## Github
Clone github repo, use master branch for deployment
```bash
git clone https://github.com/kmrozewski/OpenZtmGdansk.git
```

## Build frontend with gulp
1. Change the `API_URL` in the `ztm-spa/src/app/index.constants.js`  The domain name is taken from the nginx config location for `location /api`
```bash
'https://YOUR_DOMAIN_NAME.pl/api/'
```
2. Build optimized version of the angular application with gulp
```bash
sudo npm install && bower install --allow-root && gulp
```
3. Move `./dist` to `/var/www/html`
```bash
sudo cp -R ./dist/* /var/www/html
```
## Run java backend in background
1. Build `.jar` package with application backend
```bash
mvn clean package
```
2. Run the app
```bash
java -jar target/ztm-services-1.0-SNAPSHOT.jar --spring.config.location=config.yml > backend_logs.out 2>&1 &
# or use version without saving logs
# this will save output to nohup.out in current directory
nohup java -jar target/ztm-services-1.0-SNAPSHOT.jar --spring.config.location=config.yml &
```

The command above should return PID. Other way to check the PID is to run following command 
```bash
lsof -i :8080
```

## Configure nginx with certificate
Based on this tutorial [Teaching How to use Nginx to frontend your backend services with Trusted CA certificates on HTTPS – RedThunder.Blog](https://redthunder.blog/2017/06/14/teaching-how-to-use-nginx-to-frontend-your-backend-services-with-trusted-ca-certificates-on-https/)

**Important**: use `sudo nginx -t` after each step to check if all changes made in each step wouldn’t break anything!

1. Backup `sites-available` page.
```bash
sudo cp /etc/nginx/sites-available/default /etc/nginx/sites-available/default.bak
```
2. Add change to `/etc/nginx/sites-available/default` in order to issue the SSL certificate with Let’s Encrypt.
```nginx
location ~ /.well-known {
	allow all;
}
```
3. Issue SSL certificate with Let’s Encrypt.
```bash
sudo certbot certonly –-webroot –-webroot-path=/var/www/html -d YOUR_DOMAIN_NAME.pl
```
4. To further increase security, also generate a strong Diffie-Hellman group. To generate a 2048-bit group, use this command:
```bash
sudo openssl dhparam -out /etc/ssl/certs/dhparam.pem 2048
```
5. Add configuration snippet with SSL certificate location to `/etc/nginx/snippets/ssl-[YOUR-DOMAIN].conf`.
```nginx
ssl_certificate /etc/letsencrypt/live/YOUR_DOMAIN_NAME.pl/fullchain.pem;
ssl_certificate_key /etc/letsencrypt/live/YOUR_DOMAIN_NAME.pl/privkey.pem;
```
6. Then, create another snippet that will configure Nginx with strong SSL cipher security `/etc/nginx/snippets/ssl-params.conf`.
```nginx
ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
ssl_prefer_server_ciphers on;
ssl_ciphers "EECDH+AESGCM:EDH+AESGCM:AES256+EECDH:AES256+EDH";
ssl_ecdh_curve secp384r1;
ssl_session_cache shared:SSL:10m;
ssl_session_tickets off;
ssl_stapling on;
ssl_stapling_verify on;
resolver 8.8.8.8 8.8.4.4 valid=300s;
resolver_timeout 5s;
# disable HSTS header for now
#add_header Strict-Transport-Security “max-age=63072000; includeSubDomains; preload”;
add_header X-Frame-Options DENY;
add_header X-Content-Type-Options nosniff;
ssl_dhparam /etc/ssl/certs/dhparam.pem;
```
7. Change configuration in `/etc/nginx/sites-available/default`.
```nginx
# Default server configuration
server {
    listen 80;
    server_name YOUR_DOMAIN_NAME.pl www.YOUR_DOMAIN_NAME.pl;
    return 301 https://$host$request_uri;
}

server {
	listen 443 ssl http2 default_server;
	listen [::]:443 ssl http2 default_server;
	server_name YOUR_DOMAIN_NAME.pl www.YOUR_DOMAIN_NAME.pl;
	include snippets/ssl-YOUR_DOMAIN_NAME.pl.conf;
	include snippets/ssl-params.conf;

	location ~ /.well-known {
		allow all;
	}

	location /api {
		proxy_set_header Host $host;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_pass http://127.0.0.1:8080/;
	}

	root /var/www/html;

	# Add index.php to the list if you are using PHP
	index index.html;

	server_name _;

	location / {
		# First attempt to serve request as file, then
		# as directory, then fall back to displaying a 404.
		try_files $uri $uri/ =404;
	}
}
```


## Re-issue the SSL certificate
1. SSH into the cloud instance.
2. Re-issue the certificate for next 90 days.
```bash
sudo certbot renew
```

* Add `--dry-run` to test the renewal process. This would re-issue the certificate but it won’t save it on the disk.
3. Restart nginx.
```bash
sudo systemctl reload nginx
```
