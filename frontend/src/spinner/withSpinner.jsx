//TODO can be done with HOC
//https://www.robinwieruch.de/conditional-rendering-react#conditional-rendering-with-hoc
import React from "react";

const style = {
    width: "6rem",
    height: "6rem",
    marginTop: "1rem",
    marginBottom: "1rem",
}

export default class Spinner extends React.Component {
    render() {
        return (
            <div className="d-flex justify-content-center">
                <div className="spinner-border text-primary"
                     style={style}
                     role="status">
                    <span className="sr-only">Loading...</span>
                </div>
            </div>
        )
    }
}
