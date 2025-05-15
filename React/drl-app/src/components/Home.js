import { useEffect, useState } from "react";
import MySpinner from "./layouts/MySpinner";
import Apis, { endpoints } from "../configs/Apis";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { Alert, Button, Col, Form, NavDropdown, Row } from "react-bootstrap";

const Home = () => {
    const [activities, setActivities] = useState([]);
    const [page, setPage] = useState(1);
    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const [faculty, setFaculty] = useState([]);
    const [kw, setKw] = useState();
    const [q] = useSearchParams();
    const PAGE_SIZE = 5;



    const loadActivities = async () => { //Lấy tất cả activity
        try {
            setLoading(true);
            let url = `${endpoints['activities']}?page=${page}&pageSize=${PAGE_SIZE}`;

            let faculId = q.get('faculId'); //Lấy tất cả các faculty
            if (faculId) {
                url += `&facultyId=${faculId}`;
            }

            let kw = q.get('kw'); //Tìm kiếm hoạt động theo kw
            if (kw) {
                url += `&kw=${kw}`;
            }

            let res = await Apis.get(url);
            let newActivities = res.data;

            // Gộp dữ liệu
            if (page === 1)
                setActivities(newActivities);
            else
                setActivities(prev => [...prev, ...newActivities]);

            // Kiểm tra có còn dữ liệu hay không
            if (newActivities.length < PAGE_SIZE)
                setHasMore(false);
            else
                setHasMore(true);

        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    const loadFacul = async () => { //Lấy tất cả các khoa
        let res = await Apis.get(endpoints['faculties']);
        setFaculty(res.data);
    }

    const loadMore = () => { //Phân trang
        if (!loading && page > 0)
            setPage(page + 1);
    }

    const search = (e) => {
        e.preventDefault(); //Chặn nạp trang mặc định
        navigate(`/?kw=${kw}`);
    }

    const goToActivityDetail = (activityId) => { // Điều hướng đến ActivityDetails.js với activityId
        navigate(`/activitydetails/${activityId}`);
    }

    useEffect(() => {
        if (page > 0)
            loadActivities();
        loadFacul();
    }, [page, q]);

    useEffect(() => {
        setPage(1);
        setActivities([]);
        setHasMore(true); // reset lại trạng thái có thể load thêm
    }, [q])


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

            {activities.length === 0 && <Alert variant="info" className="mt-2">Không có hoạt động nào!</Alert>}
            <NavDropdown title="Khoa" id="basic-nav-dropdown">
                {faculty.map(f => <Link key={f.id} to={`/?faculId=${f.id}`} className="dropdown-item">{f.name}</Link>)}
            </NavDropdown>
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 max-w-6xl mx-auto px-4 py-8">
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
                        <p className="text-sm text-gray-600 mb-1">Điểm: {a.pointValue}</p>
                        <p className="text-sm text-gray-600 mb-4">Tổ chức: {a.faculty}</p>
                        <div className="flex-grow"></div>
                        <button onClick={() => goToActivityDetail(a.id)} className="w-full text-center text-blue-600 border border-blue-500 rounded-md py-2 hover:bg-blue-50 transition">
                            Chi tiết
                        </button>
                    </div>
                ))}
            </div>

            {hasMore && (
                <div className="text-center">
                    <Button onClick={loadMore} className="btn btn-primary mt-2 mb-20">Xem thêm...</Button>
                </div>
            )}

            {loading && <MySpinner />}
        </>
    )
}

export default Home;