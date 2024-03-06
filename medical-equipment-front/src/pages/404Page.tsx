import { Link } from "react-router-dom";

function NotFoundPage() {
    return (
        <div className="text-center h-75 d-flex flex-column justify-content-center">
            <h1 className="display-3 font-weight-bold text-white">404 Page not found</h1>
            <p>
                <Link to="/" type="button" className="btn btn-lg btn-primary mt-5">Back to home page</Link>
            </p>
        </div>
    )
}

export default NotFoundPage;