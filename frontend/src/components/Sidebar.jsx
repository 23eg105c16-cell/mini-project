import React from 'react';
import { NavLink } from 'react-router-dom';
import { LayoutDashboard, Users, User, ClipboardList, Bell, LogOut, ShieldAlert } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

const Sidebar = () => {
  const { user, logout } = useAuth();
  
  const studentLinks = [
    { to: '/', icon: <LayoutDashboard size={20} />, label: 'Dashboard' },
    { to: '/performance', icon: <ClipboardList size={20} />, label: 'Performance' },
  ];

  const teacherLinks = [
    { to: '/', icon: <LayoutDashboard size={20} />, label: 'Dashboard' },
    { to: '/students', icon: <Users size={20} />, label: 'Students' },
    { to: '/interventions', icon: <ShieldAlert size={20} />, label: 'Interventions' },
  ];

  const adminLinks = [
    { to: '/', icon: <LayoutDashboard size={20} />, label: 'Global Analytics' },
    { to: '/users', icon: <Users size={20} />, label: 'User Management' },
  ];

  const links = user?.role === 'ADMIN' ? adminLinks : user?.role === 'TEACHER' ? teacherLinks : studentLinks;

  return (
    <div className="sidebar animate-fade">
      <div style={{ padding: '2rem 1.5rem', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.8rem' }}>
          <div className="grad-primary" style={{ width: '32px', height: '32px', borderRadius: '8px', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <ShieldAlert color="white" size={20} />
          </div>
          <h3 className="grad-text">SARS System</h3>
        </div>
        <div className="glass" style={{ padding: '0.4rem', borderRadius: '8px', position: 'relative', cursor: 'pointer' }}>
          <Bell size={18} color="var(--text-muted)" />
          <div style={{ position: 'absolute', top: -2, right: -2, width: '8px', height: '8px', borderRadius: '50%', background: 'var(--danger)', border: '2px solid var(--surface)' }}></div>
        </div>
      </div>
      
      <nav style={{ flex: 1 }}>
        {links.map((link) => (
          <NavLink key={link.to} to={link.to} className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}>
            {link.icon}
            <span>{link.label}</span>
          </NavLink>
        ))}
      </nav>

      <div style={{ padding: '1.5rem', borderTop: '1px solid var(--border)' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1.5rem' }}>
          <div style={{ width: '40px', height: '40px', borderRadius: '50%', background: 'var(--surface-hover)', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <User size={20} />
          </div>
          <div style={{ overflow: 'hidden' }}>
            <p style={{ fontWeight: 600, fontSize: '0.9rem', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{user?.username}</p>
            <p style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>{user?.role}</p>
          </div>
        </div>
        <button className="btn" style={{ width: '100%', justifyContent: 'center', background: 'rgba(239, 68, 68, 0.1)', color: 'var(--danger)' }} onClick={logout}>
          <LogOut size={18} />
          <span>Logout</span>
        </button>
      </div>
    </div>
  );
};

export default Sidebar;
