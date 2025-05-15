import { useEffect, useState } from "react";
import MySpinner from "./layouts/MySpinner";
import Apis, { endpoints } from "../configs/Apis";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { Button, Col, Form, NavDropdown, Row } from "react-bootstrap";

const Home = () => {
    const [activities, setActivities] = useState([]);
    const [page, setPage] = useState(1);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const [faculity, setFaculity] = useState([]);
    const [kw, setKw] = useState();
    const [q] = useSearchParams();



    const loadActivities = async () => { //Lấy tất cả activity
        try {
            setLoading(true);
            let url = `${endpoints['activities']}?page=${page}`;

            let faculId = q.get('faculId');
            if (faculId) {
                url = `${url}&facultyId=${faculId}`;
            }

            let res = await Apis.get(url);
            setActivities(res.data);


        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    const loadFacul = async () => {
        let res = await Apis.get(endpoints['faculties']);
        setFaculity(res.data);
    }

    useEffect(() => {
        loadActivities();
        loadFacul();
    }, [page, q]);

    const search = (e) => {
        e.preventDefault(); //Chặn nạp trang mặc định
        navigate(`/?kw=${kw}`);
    }

    const goToActivityDetail = (activityId) => { // Điều hướng đến ActivityDetails.js với activityId
        navigate(`/activitydetails/${activityId}`); 
    }

    return (
        <>
            <Form onSubmit={search}>
                <Row>
                    <Col>
                        <Form.Control value={kw} onChange={e => setKw(e.target.value)} type="text" placeholder="Tìm hoạt động" />
                    </Col>
                    <Col>
                        <Button type="submit">Tìm</Button>
                    </Col>
                </Row>
            </Form>

            <NavDropdown title="Khoa" id="basic-nav-dropdown">
                {faculity.map(f => <Link key={f.id} to={`/?faculId=${f.id}`} className="dropdown-item">{f.name}</Link>)}
            </NavDropdown>

            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 max-w-6xl mx-auto px-4 py-8 mb-20">
                {activities.map(a => (
                    <div key={a.id} className="bg-white rounded-2xl shadow p-4 border border-gray-200 transition-transform duration-300 hover:scale-105 flex flex-col">
                        <img src={a.image} alt={a.name} className="w-full h-40 object-cover rounded-xl mb-4" />
                        <h3 className="text-lg font-bold text-blue-600 mb-1">
                            {a.name}
                        </h3>
                        <p className="text-sm text-gray-600 mb-1">ID: {a.id}</p>
                        <p className="text-sm text-gray-600 mb-1">
                            Bắt đầu: {new Date(a.startDate).toLocaleString('vi-VN', { hour: '2-digit', minute: '2-digit', day: '2-digit', month: '2-digit', year: 'numeric' })}
                        </p>
                        <p className="text-sm text-gray-600 mb-1">
                            Kết thúc: {new Date(a.endDate).toLocaleString('vi-VN', { hour: '2-digit', minute: '2-digit', day: '2-digit', month: '2-digit', year: 'numeric' })}
                        </p>
                        <p className="text-sm text-gray-600 mb-4">Điểm: {a.pointValue}</p>
                        <div className="flex-grow"></div>
                        <button onClick={() => goToActivityDetail(a.id)} className="w-full text-center text-blue-600 border border-blue-500 rounded-md py-2 hover:bg-blue-50 transition">
                            Chi tiết
                        </button>
                    </div>
                ))}
            </div>

            {loading && <MySpinner />}
        </>
    )
}

export default Home;