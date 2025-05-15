import { useEffect, useState } from "react";
import MySpinner from "./layouts/MySpinner";
import Apis, { endpoints } from "../configs/Apis";
import { FaFilter, FaSearch } from "react-icons/fa";
import { Link, useNavigate, useSearchParams } from "react-router-dom";

const Home = () => {
    const [activities, setActivities] = useState([]);
    const [page, setPage] = useState(1);
    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const [faculty, setFaculty] = useState([]);
    const [kw, setKw] = useState();
    const [fromPoint, setFromPoint] = useState('');
    const [toPoint, setToPoint] = useState('');
    const [q] = useSearchParams();
    const PAGE_SIZE = 5;



    const loadActivities = async () => {
        try {
            setLoading(true);
            let url = `${endpoints['activities']}?page=${page}&pageSize=${PAGE_SIZE}`;

            let faculId = q.get('faculId');
            if (faculId) {
                url += `&facultyId=${faculId}`;
            }

            let kw = q.get('kw');
            if (kw) {
                url += `&kw=${kw}`;
            }

            let fromPoint = q.get('fromPoint');
            if (fromPoint) {
                url += `&fromPoint=${fromPoint}`;
            }

            let toPoint = q.get('toPoint');
            if (toPoint) {
                url += `&toPoint=${toPoint}`;
            }

            let res = await Apis.get(url);
            let newActivities = res.data;

            if (page === 1)
                setActivities(newActivities);
            else
                setActivities(prev => [...prev, ...newActivities]);

            setHasMore(newActivities.length >= PAGE_SIZE);
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
        let query = `/?`;

        if (kw && kw.trim() !== '') {
            query += `kw=${kw.trim()}&`;
        }

        if (fromPoint !== '') {
            query += `fromPoint=${fromPoint}&`;
        }

        if (toPoint !== '') {
            query += `toPoint=${toPoint}&`;
        }

        navigate(query.slice(0, -1)); // loại bỏ dấu & cuối cùng
        // reset input keyword (tuỳ bạn có muốn)
        setKw('');
    };

    const goToActivityDetail = (activityId) => { // Điều hướng đến ActivityDetails.js với activityId
        navigate(`/activitydetails/${activityId}`);
    }

    const handleSelect = (e) => { // Đóng dropdown bằng cách tìm phần tử cha <details> và xóa thuộc tính "open"
        const details = e.target.closest('details');
        if (details) {
            details.removeAttribute('open');
        }
    };

    useEffect(() => {
        if (page > 0)
            loadActivities();
        loadFacul();
    }, [page, q]);

    useEffect(() => {
        setPage(1);
        setActivities([]);
        setFromPoint(q.get('fromPoint') || '');
        setToPoint(q.get('toPoint') || '');
        setHasMore(true); // reset lại trạng thái có thể load thêm
    }, [q])


    return (
        <>
            {/* Tiêu đề chính */}
            <div className="max-w-6xl mx-auto px-4 py-6 text-center border-b border-gray-300">
                <h1 className="text-4xl font-extrabold text-blue-700">OU TRAINING POINT</h1>
                <p className="text-xl font-semibold text-gray-700 mt-1">DANH SÁCH CÁC HOẠT ĐỘNG</p>
            </div>

            {/* Thanh điều khiển gồm Dropdown và Form tìm kiếm */}
            <div className="max-w-6xl mx-auto px-4 py-4 flex flex-col md:flex-row justify-between items-center gap-4 relative">
                {/* Dropdown Khoa */}
                <div className="relative max-w-xs">
                    <details className="group">
                        <summary className="cursor-pointer select-none bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition flex items-center gap-2 whitespace-nowrap">
                            <FaFilter className="text-white" />
                            Lọc theo khoa
                        </summary>
                        {/* Menu dropdown */}
                        <ul className="absolute left-0 bg-white border border-gray-200 shadow-md mt-2 rounded-md z-50 max-h-64 overflow-auto w-60 px-2">
                            {faculty.map(f => (<li key={f.id}>
                                <Link to={`/?faculId=${f.id}`} onClick={handleSelect} className="block px-4 py-2 hover:bg-gray-100 text-gray-800 no-underline whitespace-nowrap" style={{ textAlign: 'justify' }}>
                                    {f.name}
                                </Link>
                            </li>
                            ))}
                        </ul>
                    </details>
                </div>

                {/* Form tìm kiếm */}
                <form onSubmit={search} className="flex w-full md:w-auto items-center gap-2 justify-end">
                    <input value={fromPoint} onChange={e => setFromPoint(e.target.value)} type="number" min="0" placeholder="Điểm MIN" className="border border-gray-300 rounded-md px-3 py-2 min-w-[100px] focus:outline-none focus:ring-2 focus:ring-blue-400" />
                    <input value={toPoint} onChange={e => setToPoint(e.target.value)} type="number" min="0" placeholder="Điểm MAX" className="border border-gray-300 rounded-md px-3 py-2 min-w-[100px] focus:outline-none focus:ring-2 focus:ring-blue-400" />
                    <input value={kw} onChange={e => setKw(e.target.value)} type="text" placeholder="Tìm hoạt động" className="border border-gray-300 rounded-md px-4 py-2 w-48 md:w-64 focus:outline-none focus:ring-2 focus:ring-blue-400" />
                    <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition flex items-center gap-2">
                        <FaSearch />
                        Tìm
                    </button>
                </form>

            </div>

            {/* Thông báo nếu không có hoạt động */}
            {activities.length === 0 && (
                <div className="max-w-6xl mx-auto px-4">
                    <div className="bg-blue-100 text-blue-800 px-4 py-2 rounded-md mb-4">
                        Không có hoạt động nào!
                    </div>
                </div>
            )}

            {/* Danh sách hoạt động */}
            <div className={`grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 max-w-6xl mx-auto px-4 py-8 ${!hasMore ? 'mb-20' : ''}`}>
                {activities.map(a => (
                    <div key={a.id} className="bg-white rounded-2xl shadow p-4 border border-gray-200 transition-transform duration-300 hover:scale-105 flex flex-col">
                        <img src={a.image} alt={a.name} className="w-full h-40 object-cover rounded-xl mb-4" />
                        <h3 className="text-lg font-bold text-blue-600 mb-1">{a.name}</h3>
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

            {/* Nút xem thêm */}
            {hasMore && (
                <div className="text-center">
                    <button onClick={loadMore} className="bg-blue-600 text-white px-6 py-2 rounded-md mt-2 mb-20 hover:bg-blue-700 transition">
                        Xem thêm...
                    </button>
                </div>
            )}


            {/* Spinner khi đang loading */}
            {loading && <MySpinner />}
        </>
    )
}

export default Home;