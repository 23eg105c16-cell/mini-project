import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { ShieldAlert, LogIn } from 'lucide-react';
import { Navigate } from 'react-router-dom';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { user, login } = useAuth();

  if (user) return <Navigate to="/" />;
  
  const handleSubmit = (e) => {
    e.preventDefault();
    // In a real app, this would call /api/auth/signin
    // Simulating login for demo purposes
    const mockUser = {
      username: username,
      role: username === 'admin' ? 'ADMIN' : username === 'teacher' ? 'TEACHER' : 'STUDENT',
      token: 'mock-jwt-token'
    };
    login(mockUser);
  };

  return (
    <div style={{ minHeight: '100vh', width: '100%', display: 'flex', alignItems: 'center', justifyContent: 'center', background: 'radial-gradient(circle at top left, #1e293b, #0f172a)' }}>
      <div className="glass card animate-fade" style={{ width: '400px', padding: '3rem 2.5rem' }}>
        <div style={{ textAlign: 'center', marginBottom: '2.5rem' }}>
          <div className="grad-primary" style={{ width: '64px', height: '64px', borderRadius: '16px', margin: '0 auto 1.5rem', display: 'flex', justifyContent: 'center', alignItems: 'center', boxShadow: '0 0 20px rgba(99, 102, 241, 0.4)' }}>
            <ShieldAlert color="white" size={32} />
          </div>
          <h2 className="grad-text" style={{ fontSize: '1.75rem', marginBottom: '0.5rem' }}>SARS System</h2>
          <p style={{ color: 'var(--text-muted)' }}>Sign in to your account</p>
        </div>

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.25rem' }}>
          <div>
            <label style={{ display: 'block', fontSize: '0.875rem', fontWeight: 600, color: 'var(--text-muted)', marginBottom: '0.5rem' }}>Username</label>
            <input 
              type="text" 
              className="glass" 
              style={{ width: '100%', padding: '0.75rem 1rem', border: '1px solid var(--border)', color: 'white' }}
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="admin / teacher / student"
            />
          </div>
          <div>
            <label style={{ display: 'block', fontSize: '0.875rem', fontWeight: 600, color: 'var(--text-muted)', marginBottom: '0.5rem' }}>Password</label>
            <input 
              type="password" 
              className="glass" 
              style={{ width: '100%', padding: '0.75rem 1rem', border: '1px solid var(--border)', color: 'white' }}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
            />
          </div>
          
          <button type="submit" className="btn btn-primary grad-primary" style={{ marginTop: '1rem', width: '100%', justifyContent: 'center', height: '48px' }}>
            <LogIn size={20} />
            Login
          </button>
        </form>

        <p style={{ marginTop: '2rem', textAlign: 'center', fontSize: '0.875rem', color: 'var(--text-muted)' }}>
          Contact support for new account requests
        </p>
      </div>
    </div>
  );
};

export default Login;
