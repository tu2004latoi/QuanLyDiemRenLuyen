function deleteActivity(endpoint, id) {
    // Kiểm tra nếu id không hợp lệ
    if (!id) {
        alert("ID không hợp lệ.");
        return;
    }

    if (confirm("Bạn chắc chắn muốn xóa?") === true) {
        fetch(`${endpoint}/${id}`, {
            method: "DELETE"
        })
        .then(res => {
            if (res.status === 204) {
                alert("Xóa thành công");
                window.location.href = "/drlapp/";
            } else {
                // Xử lý chi tiết lỗi từ server
                res.text().then(errorMessage => {
                    alert("Hệ thống bị lỗi: " + errorMessage);
                });
            }
        })
        .catch(error => {
            // Xử lý lỗi khi không thể thực hiện yêu cầu fetch
            console.error("Có lỗi xảy ra khi xóa:", error);
            alert("Có lỗi xảy ra khi xóa.");
        });
    }
}
