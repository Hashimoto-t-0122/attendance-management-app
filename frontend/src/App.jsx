import { useState } from "react";
import "./App.css";

function App() {
  const API_BASE_URL = "https://attendance-management-backend-8pdx.onrender.com";
  const [employeeCode, setEmployeeCode] = useState("");
  const [password, setPassword] = useState("");
  const [user, setUser] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");
  const [attendanceMessage, setAttendanceMessage] = useState("");
  const [attendanceHistory, setAttendanceHistory] = useState([]);

  const handleLogin = async () => {
    setErrorMessage("");

  try {
    const response = await fetch(
      `${API_BASE_URL}/api/auth/login`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          employeeCode: employeeCode,
          password: password,
        }),
      }
    );

    if (!response.ok) {
      throw new Error("ログインに失敗しました");
    }

    const data = await response.json();

    console.log("ログイン成功:", data);
    setUser(data);
    fetchAttendanceHistory(data.id);
  } catch (error) {
    console.error("ログインエラー:", error);

      setErrorMessage(
    "社員番号またはパスワードが正しくありません"
  );
  }
};

const handleClockIn = async () => {
  setAttendanceMessage("");

  try {
    const response = await fetch(
      `${API_BASE_URL}/api/attendance/clock-in?employeeId=${user.id}`,
      {
        method: "POST",
      }
    );

    if (!response.ok) {
  const errorData = await response.json();

  throw new Error(
    errorData.message || "出勤登録に失敗しました"
  );
}

    const data = await response.json();

    console.log("出勤成功:", data);

    setAttendanceMessage("出勤しました");
    fetchAttendanceHistory(user.id);
 } catch (error) {
  console.error("出勤エラー:", error);

  setAttendanceMessage(error.message);
}
};

const handleClockOut = async () => {
  setAttendanceMessage("");

  try {
    const response = await fetch(
      `${API_BASE_URL}/api/attendance/clock-out?employeeId=${user.id}`,
      {
        method: "POST",
      }
    );

    if (!response.ok) {
  const errorData = await response.json();

  throw new Error(
    errorData.message || "退勤登録に失敗しました"
  );
}

    const data = await response.json();

    console.log("退勤成功:", data);

    setAttendanceMessage("退勤しました");
    fetchAttendanceHistory(user.id);
  } catch (error) {
  console.error("退勤エラー:", error);

  setAttendanceMessage(error.message);
}
};

const handleLogout = () => {
  setUser(null);
  setEmployeeCode("");
  setPassword("");
  setAttendanceMessage("");
  setAttendanceHistory([]);
};

const fetchAttendanceHistory = async (employeeId) => {
  try {
    const response = await fetch(
      `${API_BASE_URL}/api/attendance/history?employeeId=${employeeId}`
    );

    if (!response.ok) {
      throw new Error("勤怠履歴の取得に失敗しました");
    }

    const data = await response.json();

    console.log("勤怠履歴:", data);

    setAttendanceHistory(data);
  } catch (error) {
    console.error("勤怠履歴取得エラー:", error);
  }
};

const formatTime = (dateTime) => {
  if (!dateTime) {
    return "未退勤";
  }

  return dateTime.substring(11, 19);
};

if (user) {
  return (
    <main className="app-container">
      <div className="card">
        <h1>勤怠管理アプリ</h1>

        <h2>{user.name}さん</h2>

        <p className="employee-code">
          社員番号：{user.employeeCode}
        </p>

        <div className="button-group">
          <button onClick={handleClockIn}>出勤</button>
          <button onClick={handleClockOut}>退勤</button>
          <button onClick={handleLogout}>ログアウト</button>
        </div>

        {attendanceMessage && (
          <p className="message">
            {attendanceMessage}
          </p>
        )}

        <h2>勤怠履歴</h2>

        {attendanceHistory.length === 0 ? (
          <p>勤怠履歴はまだありません。</p>
        ) : (
          <table className="attendance-table">
            <thead>
              <tr>
                <th>勤務日</th>
                <th>出勤時刻</th>
                <th>退勤時刻</th>
              </tr>
            </thead>

            <tbody>
              {attendanceHistory.map((record) => (
                <tr key={record.id}>
                  <td>{record.workDate}</td>
                  <td>{formatTime(record.clockInTime)}</td>
                  <td>{formatTime(record.clockOutTime)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </main>
  );
}
  return (
  <main className="app-container">
    <div className="card login-card">
      <h1>勤怠管理アプリ</h1>
      <h2>ログイン</h2>

      <div className="form-group">
        <label>社員番号</label>
        <input
          type="text"
          value={employeeCode}
          onChange={(e) => setEmployeeCode(e.target.value)}
        />
      </div>

      <div className="form-group">
        <label>パスワード</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>

      <button onClick={handleLogin}>
        ログイン
      </button>

      {errorMessage && (
        <p className="error-message">
          {errorMessage}
        </p>
      )}
    </div>
  </main>
);
}

export default App;