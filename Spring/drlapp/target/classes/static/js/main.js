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
                        window.location.href = "/drlapp/activities";
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

function deleteUser(endpoint, id) {
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
                        location.reload();
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

function deleteActivityRegistration(endpoint, id) {
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
                        location.reload();
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

function ActivityRegistration(endpoint, id) {
    // Kiểm tra nếu id không hợp lệ
    if (!id) {
        alert("ID không hợp lệ.");
        return;
    }

    if (confirm("Bạn chắc chắn muốn tham gia?") === true) {
        fetch(`${endpoint}/${id}`, {
            method: "POST"
        })
                .then(res => {
                    if (res.status === 204) {
                        alert("Đăng ký thành công");
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

function updateTrainingPoint(endpoint, id, action) {
    // Kiểm tra nếu id không hợp lệ
    if (!id) {
        alert("ID không hợp lệ.");
        return;
    }

    // Xác nhận hành động từ người dùng
    const confirmationMessage = action === "confirm" ? "Bạn có muốn xác nhận không?" : "Bạn có muốn từ chối không?";
    if (confirm(confirmationMessage) === true) {
        // Gửi yêu cầu PATCH
        fetch(`${endpoint}/${action}/${id}`, {
            method: "PATCH"
        })
                .then(res => {
                    if (res.ok) {
                        return res.text(); // <- vì backend trả về plain text
                    } else {
                        return res.text().then(err => {
                            throw new Error(err);
                        });
                    }
                })
                .then(msg => {
                    alert(msg); // hiện thông báo "Cập nhật thành công"
                    location.reload();
                })
                .catch(error => {
                    console.error("Lỗi:", error);
                    alert("Lỗi từ hệ thống: " + error.message);
                });
    }
}

function deleteEvidence(endpoint, id) {
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
                        location.reload();
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

function deleteMyActivity(endpoint, id) {
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
                        location.reload();
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
