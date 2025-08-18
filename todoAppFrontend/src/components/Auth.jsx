import React, { useState } from "react";
import { useNavigate } from "react-router";
import toast from "react-hot-toast";

const API_BASE = "http://localhost:8081/todo";

function Auth() {
  const [form, setForm] = useState({ username: "", password: "" });
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.id]: e.target.value });
  };

  const handleSignup = async () => {
    if (!form.username || !form.password) {
      toast.error("Enter username & password");
      return;
    }
    setLoading(true);
    try {
      const res = await fetch(`${API_BASE}/user/sign-up`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (res.ok) {
        toast.success("Account created! Now log in.");
      } else {
        const msg = await res.text();
        toast.error(msg);
      }
    } catch (err) {
      toast.error("Server error");
    }
    setLoading(false);
  };

  const handleLogin = async () => {
    if (!form.username || !form.password) {
      toast.error("Enter username & password");
      return;
    }
    setLoading(true);
    try {
      const res = await fetch(`${API_BASE}/user/log-in`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (res.ok) {
        const token = await res.text();
        localStorage.setItem("token", token);
        localStorage.setItem("username", form.username);
        toast.success("Login successful!");
        navigate("/task");
      } else {
        const msg = await res.text();
        toast.error(msg);
      }
    } catch (err) {
      toast.error("Server error");
    }
    setLoading(false);
  };

  return (
    <div className="auth-container">
      <div className="authInputBox">
        <div className="authInput">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            value={form.username}
            onChange={handleChange}
            placeholder="username"
          />
        </div>
        <div className="authInput">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={form.password}
            onChange={handleChange}
            placeholder="password"
          />
        </div>
        <div className="authBtn">
          <button
            className="authRegisterBtns"
            onClick={handleSignup}
            disabled={loading}
          >
            Sign up
          </button>
          <button
            className="authRegisterBtns"
            onClick={handleLogin}
            disabled={loading}
          >
            Log in
          </button>
        </div>
      </div>
    </div>
  );
}

export default Auth;
