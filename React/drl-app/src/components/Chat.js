import React, { useContext, useEffect, useState, useRef } from "react";
import {
  collection,
  query,
  where,
  orderBy,
  addDoc,
  onSnapshot,
  serverTimestamp,
} from "firebase/firestore";
import { db } from "../configs/Firebase";
import { MyUserContext } from "../configs/MyContexts";

function Chat() {
  const [currentUser, setCurrentUser] = useState(null);
  const [chatUsers, setChatUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [messages, setMessages] = useState([]);
  const [inputText, setInputText] = useState("");
  const user = useContext(MyUserContext);
  const messagesEndRef = useRef(null);

  useEffect(() => {
    if (user && user.email) setCurrentUser(user.email);
    else setCurrentUser(null);
  }, [user]);

  useEffect(() => {
    if (!currentUser) return;

    const q = query(
      collection(db, "conversations"),
      where("participants", "array-contains", currentUser)
    );

    const unsubscribe = onSnapshot(q, (snapshot) => {
      const usersSet = new Set();

      snapshot.docs.forEach((doc) => {
        const data = doc.data();
        if (data.participants && data.participants.length === 2) {
          const otherUser = data.participants.find((p) => p !== currentUser);
          if (otherUser) usersSet.add(otherUser);
        }
      });

      setChatUsers(Array.from(usersSet));
    });

    return () => unsubscribe();
  }, [currentUser]);

  useEffect(() => {
    if (!selectedUser || !currentUser) {
      setMessages([]);
      return;
    }

    const conversationId = [currentUser, selectedUser].sort().join("_");
    const messagesRef = collection(
      db,
      "conversations",
      conversationId,
      "messages"
    );
    const qMessages = query(messagesRef, orderBy("timestamp", "asc"));

    const unsubscribe = onSnapshot(qMessages, (snapshot) => {
      const msgs = snapshot.docs.map((doc) => ({
        id: doc.id,
        ...doc.data(),
        timestamp: doc.data().timestamp?.toDate() || new Date(),
      }));
      setMessages(msgs);
    });

    return () => unsubscribe();
  }, [selectedUser, currentUser]);

  // Scroll xuống cuối mỗi khi messages thay đổi
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const sendMessage = async () => {
    if (!inputText.trim() || !selectedUser || !currentUser) return;

    const conversationId = [currentUser, selectedUser].sort().join("_");

    try {
      await addDoc(
        collection(db, "conversations", conversationId, "messages"),
        {
          sender: currentUser,
          sender_name: user.name,
          text: inputText.trim(),
          timestamp: serverTimestamp(),
        }
      );
      setInputText("");
    } catch (error) {
      console.error("Lỗi gửi tin nhắn:", error);
    }
  };

  return (
    <div
      style={{
        display: "flex",
        height: "80vh",
        width: "900px",
        margin: "20px auto",
        borderRadius: "12px",
        boxShadow: "0 4px 12px rgb(0 0 0 / 0.1)",
        fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
        overflow: "hidden",
        backgroundColor: "#fafafa",
      }}
    >
      {/* Danh sách chat */}
      <aside
        style={{
          width: "250px",
          borderRight: "1px solid #ddd",
          backgroundColor: "#fff",
          padding: "20px",
          boxSizing: "border-box",
          overflowY: "auto",
        }}
      >
        <h2 style={{ marginBottom: "15px", fontWeight: "600", color: "#333" }}>
          Danh sách chat
        </h2>
        {chatUsers.length === 0 && (
          <p style={{ color: "#888", fontStyle: "italic" }}>Chưa có tin nhắn</p>
        )}
        <ul style={{ listStyle: "none", padding: 0, margin: 0 }}>
          {chatUsers.map((userEmail) => (
            <li
              key={userEmail}
              onClick={() => setSelectedUser(userEmail)}
              style={{
                padding: "12px 15px",
                marginBottom: "8px",
                borderRadius: "10px",
                cursor: "pointer",
                backgroundColor:
                  selectedUser === userEmail ? "#1976d2" : "#f1f3f5",
                color: selectedUser === userEmail ? "#fff" : "#333",
                fontWeight: selectedUser === userEmail ? "600" : "400",
                transition: "background-color 0.25s",
                userSelect: "none",
              }}
              onMouseEnter={(e) => {
                if (selectedUser !== userEmail)
                  e.currentTarget.style.backgroundColor = "#e3e6ea";
              }}
              onMouseLeave={(e) => {
                if (selectedUser !== userEmail)
                  e.currentTarget.style.backgroundColor = "#f1f3f5";
              }}
            >
              {userEmail}
            </li>
          ))}
        </ul>
      </aside>

      {/* Khu vực chat */}
      <section
        style={{
          flex: 1,
          display: "flex",
          flexDirection: "column",
          backgroundColor: "#fff",
          padding: "20px",
          boxSizing: "border-box",
          position: "relative",
        }}
      >
        {!selectedUser ? (
          <div
            style={{
              flex: 1,
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              color: "#999",
              fontSize: "1.1rem",
              fontStyle: "italic",
            }}
          >
            Chọn một người để bắt đầu chat
          </div>
        ) : (
          <>
            <header
              style={{
                paddingBottom: "12px",
                borderBottom: "1px solid #eee",
                marginBottom: "15px",
                fontWeight: "600",
                fontSize: "1.25rem",
                color: "#1976d2",
              }}
            >
              Chat với {selectedUser}
            </header>

            <div
              style={{
                flex: 1,
                overflowY: "auto",
                paddingRight: "10px",
                scrollBehavior: "smooth",
              }}
            >
              {messages.map((msg) => {
                const isCurrentUser = msg.sender === currentUser;
                return (
                  <div
                    key={msg.id}
                    style={{
                      display: "flex",
                      flexDirection: "column",
                      alignItems: isCurrentUser ? "flex-end" : "flex-start",
                      marginBottom: "12px",
                    }}
                  >
                    {/* Hiển thị tên người gửi */}
                    <div
                      style={{
                        fontSize: "0.8rem",
                        fontWeight: "600",
                        marginBottom: "4px",
                        color: isCurrentUser ? "#1976d2" : "#555",
                        userSelect: "none",
                      }}
                    >
                      {msg.sender_name || msg.sender}
                    </div>

                    {/* Nội dung tin nhắn */}
                    <div
                      style={{
                        maxWidth: "70%",
                        backgroundColor: isCurrentUser ? "#1976d2" : "#e0e0e0",
                        color: isCurrentUser ? "#fff" : "#333",
                        padding: "10px 18px",
                        borderRadius: isCurrentUser
                          ? "18px 18px 0 18px"
                          : "18px 18px 18px 0",
                        boxShadow: "0 1px 3px rgba(0,0,0,0.1)",
                        wordBreak: "break-word",
                        fontSize: "1rem",
                        lineHeight: "1.4",
                      }}
                    >
                      {msg.text}
                      <div
                        style={{
                          fontSize: "0.7rem",
                          opacity: 0.6,
                          marginTop: "6px",
                          textAlign: "right",
                          fontWeight: "400",
                          userSelect: "none",
                        }}
                      >
                        {msg.timestamp.toLocaleTimeString([], {
                          hour: "2-digit",
                          minute: "2-digit",
                        })}
                      </div>
                    </div>
                  </div>
                );
              })}

              <div ref={messagesEndRef} />
            </div>

            <div
              style={{
                marginTop: "15px",
                display: "flex",
                alignItems: "center",
                gap: "10px",
              }}
            >
              <input
                type="text"
                placeholder={`Nhắn tin cho ${selectedUser}...`}
                value={inputText}
                onChange={(e) => setInputText(e.target.value)}
                onKeyDown={(e) => {
                  if (e.key === "Enter") sendMessage();
                }}
                style={{
                  flex: 1,
                  padding: "12px 16px",
                  fontSize: "1rem",
                  borderRadius: "25px",
                  border: "1px solid #ccc",
                  outline: "none",
                  transition: "border-color 0.3s",
                }}
                onFocus={(e) => (e.target.style.borderColor = "#1976d2")}
                onBlur={(e) => (e.target.style.borderColor = "#ccc")}
              />
              <button
                onClick={sendMessage}
                style={{
                  padding: "12px 25px",
                  backgroundColor: "#1976d2",
                  border: "none",
                  borderRadius: "25px",
                  color: "#fff",
                  fontWeight: "600",
                  cursor: "pointer",
                  boxShadow: "0 3px 6px rgba(25,118,210,0.4)",
                  transition: "background-color 0.3s",
                }}
                onMouseEnter={(e) =>
                  (e.currentTarget.style.backgroundColor = "#135ba1")
                }
                onMouseLeave={(e) =>
                  (e.currentTarget.style.backgroundColor = "#1976d2")
                }
              >
                Gửi
              </button>
            </div>
          </>
        )}
      </section>
    </div>
  );
}

export default Chat;
