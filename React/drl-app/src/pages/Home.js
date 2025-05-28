import { useEffect, useState } from "react";
import MySpinner from "../components/layouts/MySpinner";
import Apis, { endpoints } from "../configs/Apis";
import { FaFilter, FaSearch } from "react-icons/fa";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { useTranslation } from "react-i18next";

const Home = () => {
  const { t } = useTranslation();
  const [activities, setActivities] = useState([]);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const [loading, setLoading] = useState(false);
  const [faculty, setFaculty] = useState([]);
  const [kw, setKw] = useState("");
  const [fromPoint, setFromPoint] = useState("");
  const [toPoint, setToPoint] = useState("");
  const [q] = useSearchParams();
  const navigate = useNavigate();
  const PAGE_SIZE = 6;

  const loadActivities = async () => {
    try {
      setLoading(true);

      await new Promise((resolve) => setTimeout(resolve, 500));

      let url = `${endpoints["activities"]}?page=${page}&pageSize=${PAGE_SIZE}`;
      const faculId = q.get("faculId");
      const kw = q.get("kw");
      const fromPoint = q.get("fromPoint");
      const toPoint = q.get("toPoint");

      if (faculId) url += `&facultyId=${faculId}`;
      if (kw) url += `&kw=${kw}`;
      if (fromPoint) url += `&fromPoint=${fromPoint}`;
      if (toPoint) url += `&toPoint=${toPoint}`;

      let res = await Apis.get(url);
      let newActivities = res.data;

      if (page === 1) setActivities(newActivities);
      else setActivities((prev) => [...prev, ...newActivities]);

      setHasMore(newActivities.length >= PAGE_SIZE);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const loadFacul = async () => {
    let res = await Apis.get(endpoints["faculties"]);
    setFaculty(res.data);
  };

  const search = (e) => {
    e.preventDefault();
    let query = `/?`;
    if (kw.trim() !== "") query += `kw=${kw.trim()}&`;
    if (fromPoint !== "") query += `fromPoint=${fromPoint}&`;
    if (toPoint !== "") query += `toPoint=${toPoint}&`;
    navigate(query.slice(0, -1));
    setPage(1);
  };

  const goToActivityDetail = (activityId) => {
    navigate(`/activitydetails/${activityId}`);
  };

  const handleSelect = (e) => {
    const details = e.target.closest("details");
    if (details) details.removeAttribute("open");
  };

  useEffect(() => {
    setFromPoint(q.get("fromPoint") || "");
    setToPoint(q.get("toPoint") || "");
    setKw(q.get("kw") || "");
    setPage(1);
    setActivities([]);
    setHasMore(true);
  }, [q]);

  useEffect(() => {
    if (page > 0) loadActivities();
    loadFacul();
  }, [page, q]);

  useEffect(() => {
    const handleScroll = () => {
      if (
        window.innerHeight + window.scrollY >= document.body.offsetHeight - 300 &&
        !loading &&
        hasMore
      ) {
        setPage((prev) => prev + 1);
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [loading, hasMore]);

  return (
    <>
      <div className="max-w-6xl mx-auto px-4 py-6 text-center border-b border-gray-200">
        <h1 className="text-4xl font-extrabold text-blue-700 drop-shadow-lg">
          {t("title")}
        </h1>
        <p className="text-xl font-medium text-gray-600 mt-2">
          {t("subtitle")}
        </p>
      </div>

      <div className="max-w-6xl mx-auto px-4 py-4 flex flex-col md:flex-row justify-between items-center gap-4">
        <div className="relative max-w-xs">
          <details className="group">
            <summary className="cursor-pointer select-none bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition flex items-center gap-2">
              <FaFilter />
              {t("filterByFaculty")}
            </summary>
            <ul className="absolute left-0 bg-white border border-gray-200 shadow-xl mt-2 rounded-md z-50 max-h-64 overflow-auto w-60 px-2">
              {faculty.map((f) => (
                <li key={f.id}>
                  <Link
                    to={`/?faculId=${f.id}`}
                    onClick={handleSelect}
                    className="block px-4 py-2 hover:bg-gray-100 text-gray-800 rounded-md transition"
                  >
                    {f.name}
                  </Link>
                </li>
              ))}
            </ul>
          </details>
        </div>

        <form
          onSubmit={search}
          className="flex flex-wrap gap-2 items-center justify-end w-full md:w-auto"
        >
          <input
            value={fromPoint}
            onChange={(e) => setFromPoint(e.target.value)}
            type="number"
            min="0"
            placeholder={t("minPoint")}
            className="border border-gray-300 rounded-md px-3 py-2 w-24 focus:ring-2 focus:ring-blue-400"
          />
          <input
            value={toPoint}
            onChange={(e) => setToPoint(e.target.value)}
            type="number"
            min="0"
            placeholder={t("maxPoint")}
            className="border border-gray-300 rounded-md px-3 py-2 w-24 focus:ring-2 focus:ring-blue-400"
          />
          <input
            value={kw}
            onChange={(e) => setKw(e.target.value)}
            type="text"
            placeholder={t("searchPlaceholder")}
            className="border border-gray-300 rounded-md px-4 py-2 w-48 md:w-64 focus:ring-2 focus:ring-blue-400"
          />
          <button
            type="submit"
            className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 flex items-center gap-2 transition"
          >
            <FaSearch />
            {t("searchButton")}
          </button>
        </form>
      </div>

      {activities.length === 0 && !loading && (
        <div className="max-w-6xl mx-auto px-4">
          <div className="bg-yellow-100 text-yellow-800 px-4 py-2 rounded-md mb-4 text-center">
            {t("noActivities")}
          </div>
        </div>
      )}

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 max-w-6xl mx-auto px-4 py-8 mb-20">
        {activities.map((a) => (
          <div
            key={a.id}
            className="bg-white rounded-2xl shadow-md hover:shadow-xl hover:scale-[1.02] transform transition-transform duration-300 p-4 flex flex-col"
          >
            <img
              src={a.image}
              alt={a.name}
              className="w-full h-40 object-cover rounded-xl mb-4"
            />
            <h3 className="text-lg font-bold text-blue-600 mb-1">{a.name}</h3>
            <p className="text-sm text-gray-600">
              {t("start")} {new Date(a.startDate).toLocaleString("vi-VN")}
            </p>
            <p className="text-sm text-gray-600">
              {t("end")} {new Date(a.endDate).toLocaleString("vi-VN")}
            </p>
            <p className="text-sm text-gray-600">
              {t("point")} {a.pointValue}
            </p>
            <p className="text-sm text-gray-600 mb-4">
              {t("faculty")} {a.faculty}
            </p>
            <div className="flex-grow" />
            <button
              onClick={() => goToActivityDetail(a.id)}
              className="mt-2 w-full text-blue-600 border border-blue-500 rounded-md py-2 hover:bg-blue-50 transition"
            >
              {t("details")}
            </button>
          </div>
        ))}
      </div>
      {loading && (
        <div className="flex justify-center mb-20">
          <MySpinner />
        </div>
      )}
    </>
  );
};

export default Home;
