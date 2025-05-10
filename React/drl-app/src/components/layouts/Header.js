import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link } from "react-router-dom";

const Header = () => {
    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand href="#home" className="font-bold">OU TRANING POINT</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Link to="/" className="nav-link">Trang chủ</Link>
                        <Link to="/login" className="nav-link text-danger">Đăng nhập</Link>
                        <Link to="/register" className="nav-link text-success" >Đăng ký</Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default Header;