import { useContext } from "react";
import { Button, Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import { MyDispatcherContext, MyUserContext } from "../../configs/MyContexts";

const Header = () => {
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatcherContext);

    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand href="#home" className="font-bold">OU TRANING POINT</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">

                        {user === null ?
                            <>
                                <Link to="/login" className="nav-link text-danger">Đăng nhập</Link>
                                <Link to="/register" className="nav-link text-success" >Đăng ký</Link>
                            </> : <>
                                <Link to="/" className="nav-link text-danger">
                                    <img src={user.avatar} width="20" className="rounded-circle" />
                                    Chào {user.username}
                                </Link>
                                <Button variant="danger" onClick={() => dispatch({"type": "logout"})}>Đăng xuất</Button>
                            </>}

                        <Link to="/" className="nav-link">Trang chủ</Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default Header;