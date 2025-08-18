import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import toast from "react-hot-toast";

const API_BASE = "http://localhost:8081/todo";

function Task() {
  const [tasks, setTasks] = useState([]);
  const [newTask, setNewTask] = useState("");
  const [editingTask, setEditingTask] = useState(null);
  const [editText, setEditText] = useState("");
  const [editUser, setEditUser] = useState({ username: "", password: "" });
  const [showAccountModal, setShowAccountModal] = useState(false);

  const username = localStorage.getItem("username");
  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  // Fetch tasks
  const fetchTasks = async () => {
    try {
      const res = await fetch(`${API_BASE}/task/${username}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (res.ok) {
        setTasks(await res.json());
      } else toast.error("Failed to load tasks");
    } catch {
      toast.error("Server error");
    }
  };

  // Add task
  const addTask = async () => {
    if (!newTask.trim()) return;
    try {
      const res = await fetch(`${API_BASE}/task/${username}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ title: newTask, completed: false }),
      });
      if (res.ok) {
        setNewTask("");
        fetchTasks();
        toast.success("Task added!");
      }
    } catch {
      toast.error("Server error");
    }
  };

  // Toggle task complete
  const toggleTask = async (task) => {
    try {
      const res = await fetch(`${API_BASE}/task/${username}/${task.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ ...task, completed: !task.completed }),
      });
      if (res.ok) fetchTasks();
    } catch {
      toast.error("Failed to update");
    }
  };

  // Start editing a task
  const startEditTask = (task) => {
    setEditingTask(task);
    setEditText(task.title);
  };

  // Update task
  const updateTask = async () => {
    if (!editingTask) return;
    try {
      const res = await fetch(`${API_BASE}/task/${username}/${editingTask.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ ...editingTask, title: editText }),
      });
      if (res.ok) {
        setEditingTask(null);
        setEditText("");
        fetchTasks();
        toast.success("Task updated");
      }
    } catch {
      toast.error("Failed to update task");
    }
  };

  // Delete task
  const deleteTask = async (id) => {
    try {
      const res = await fetch(`${API_BASE}/task/${username}/${id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });
      if (res.ok) {
        fetchTasks();
        toast.success("Deleted");
      }
    } catch {
      toast.error("Failed to delete");
    }
  };

  // Update account
  const updateAccount = async () => {
    if (!editUser.username && !editUser.password) {
      toast.error("Fill at least one field");
      return;
    }
    try {
      const res = await fetch(`${API_BASE}/user/${username}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(editUser),
      });
      if (res.ok) {
        toast.success("Account updated, please login again");
        localStorage.clear();
        navigate("/");
      } else {
        toast.error("Failed to update account");
      }
    } catch {
      toast.error("Server error");
    }
  };

  // Delete account
  const deleteAccount = async () => {
    if (!window.confirm("Are you sure? This will delete your account.")) return;
    try {
      const res = await fetch(`${API_BASE}/user/${username}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });
      if (res.ok) {
        toast.success("Account deleted");
        localStorage.clear();
        navigate("/");
      } else {
        toast.error("Failed to delete account");
      }
    } catch {
      toast.error("Server error");
    }
  };

  useEffect(() => {
    if (!username || !token) navigate("/");
    fetchTasks();
  }, []);

  return (
    <div className="taskpage">
      {/* Left user info box */}
      <div className="authInfoBox">
        <div className="taskInfo">
          <h2>{username}</h2>
          <hr />
          <div className="taskInfoDiv">
            <h3>Total: {tasks.length}</h3>
            <h3>Completed: {tasks.filter((t) => t.completed).length}</h3>
            <h3>Pending: {tasks.filter((t) => !t.completed).length}</h3>
          </div>
        </div>

        <div className="authInfo">
          <button
            onClick={() => {
              localStorage.clear();
              navigate("/");
              toast.success("Logged out");
            }}
          >
            Logout
          </button>
          <button onClick={() => setShowAccountModal(true)}>
            Update Account
          </button>
        </div>
      </div>

      {/* Right task box */}
      <div className="taskContainer">
        <h3>Your Tasks</h3>
        <div className="taskInput">
          <input
            type="text"
            placeholder="New Task...."
            value={newTask}
            onChange={(e) => setNewTask(e.target.value)}
          />
          <button onClick={addTask}>+</button>
        </div>
        <div className="taskList">
          {tasks.map((task) => (
            <div
              key={task.id}
              className={`taskItem ${task.completed ? "taskCompleted" : ""}`}
            >
              <input
                type="checkbox"
                checked={task.completed}
                onChange={() => toggleTask(task)}
              />
              {editingTask?.id === task.id ? (
                <>
                  <input
                    value={editText}
                    onChange={(e) => setEditText(e.target.value)}
                  />
                  <button onClick={updateTask}>Save</button>
                  <button onClick={() => setEditingTask(null)}>Cancel</button>
                </>
              ) : (
                <>
                  <p>{task.title}</p>
                  <div className="taskActions">
                    <button onClick={() => deleteTask(task.id)}>x</button>
                  </div>
                </>
              )}
            </div>
          ))}
        </div>
      </div>

      {/* Account Modal */}
      {showAccountModal && (
        <div className="modalOverlay">
          <div className="modalContent">
            <h3>Update Account</h3>
            <input
              type="text"
              placeholder="New Username"
              value={editUser.username}
              onChange={(e) =>
                setEditUser({ ...editUser, username: e.target.value })
              }
            />
            <input
              type="password"
              placeholder="New Password"
              value={editUser.password}
              onChange={(e) =>
                setEditUser({ ...editUser, password: e.target.value })
              }
            />
            <div className="modalButtons">
              <button onClick={updateAccount}>Save</button>
              <button onClick={deleteAccount} style={{ background: "red" }}>
                Delete Account
              </button>
              <button onClick={() => setShowAccountModal(false)}>Close</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default Task;
