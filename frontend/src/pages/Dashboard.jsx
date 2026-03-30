import React from 'react';
import { useAuth } from '../context/AuthContext';
import { 
  TrendingUp, Users, AlertCircle, CheckCircle, 
  BookOpen, Calendar, Award, Download
} from 'lucide-react';
import { 
  BarChart, Bar, XAxis, YAxis, CartesianGrid, 
  Tooltip, ResponsiveContainer, LineChart, Line,
  PieChart, Pie, Cell
} from 'recharts';

const Dashboard = () => {
  const { user } = useAuth();

  const stats = [
    { label: 'Total Students', value: '1,240', icon: <Users size={20} />, trend: '+4%' },
    { label: 'High Risk', value: '42', icon: <AlertCircle size={20} color="var(--danger)" />, trend: '-2%' },
    { label: 'Active Interventions', value: '18', icon: <CheckCircle size={20} color="var(--success)" />, trend: '+12%' },
    { label: 'Avg Attendance', value: '88%', icon: <Calendar size={20} />, trend: '+1%' },
  ];

  const riskData = [
    { name: 'Low', value: 850, color: 'var(--success)' },
    { name: 'Medium', value: 348, color: 'var(--warning)' },
    { name: 'High', value: 42, color: 'var(--danger)' },
  ];

  const performanceData = [
    { name: 'Week 1', score: 65 },
    { name: 'Week 2', score: 72 },
    { name: 'Week 3', score: 68 },
    { name: 'Week 4', score: 85 },
    { name: 'Week 5', score: 82 },
    { name: 'Week 6', score: 90 },
  ];

  return (
    <div className="animate-fade">
      <header style={{ marginBottom: '2.5rem', display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end' }}>
        <div>
          <p style={{ color: 'var(--text-muted)', fontWeight: 500, marginBottom: '0.25rem' }}>Welcome back, {user?.username}</p>
          <h1>{user?.role} Dashboard</h1>
        </div>
        <div className="btn glass" style={{ padding: '0.5rem 1rem', fontSize: '0.875rem' }}>
          <Calendar size={16} />
          {new Date().toLocaleDateString('en-US', { month: 'long', day: 'numeric' })}
        </div>
      </header>

      <div className="grid grid-cols-3" style={{ gridTemplateColumns: 'repeat(4, 1fr)', marginBottom: '2.5rem' }}>
        {stats.map((stat, i) => (
          <div key={i} className="glass card" style={{ position: 'relative', overflow: 'hidden' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '1rem' }}>
              <div style={{ padding: '0.75rem', borderRadius: '12px', background: 'rgba(255,255,255,0.05)' }}>
                {stat.icon}
              </div>
              <span style={{ fontSize: '0.75rem', fontWeight: 600, color: stat.trend.startsWith('+') ? 'var(--success)' : 'var(--danger)' }}>
                {stat.trend}
              </span>
            </div>
            <p style={{ fontSize: '0.875rem', color: 'var(--text-muted)', fontWeight: 500 }}>{stat.label}</p>
            <h2 style={{ fontSize: '1.75rem', marginTop: '0.25rem' }}>{stat.value}</h2>
          </div>
        ))}
      </div>

      <div className="grid grid-cols-2" style={{ marginBottom: '2.5rem' }}>
        <div className="glass card" style={{ height: '400px' }}>
          <h3 style={{ marginBottom: '1.5rem' }}>Risk Distribution</h3>
          <ResponsiveContainer width="100%" height="85%">
            <PieChart>
              <Pie
                data={riskData}
                innerRadius={60}
                outerRadius={80}
                paddingAngle={5}
                dataKey="value"
              >
                {riskData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={entry.color} />
                ))}
              </Pie>
              <Tooltip 
                contentStyle={{ background: 'var(--surface)', border: '1px solid var(--border)', borderRadius: '8px' }}
              />
            </PieChart>
          </ResponsiveContainer>
          <div style={{ display: 'flex', justifyContent: 'center', gap: '1.5rem', marginTop: '-1rem' }}>
            {riskData.map(d => (
              <div key={d.name} style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                <div style={{ width: '8px', height: '8px', borderRadius: '50%', background: d.color }}></div>
                <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>{d.name}</span>
              </div>
            ))}
          </div>
        </div>

        <div className="glass card" style={{ height: '400px' }}>
          <h3 style={{ marginBottom: '1.5rem' }}>Academic Performance Trend</h3>
          <ResponsiveContainer width="100%" height="85%">
            <LineChart data={performanceData}>
              <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.05)" />
              <XAxis dataKey="name" stroke="var(--text-muted)" fontSize={12} tickLine={false} axisLine={false} />
              <YAxis stroke="var(--text-muted)" fontSize={12} tickLine={false} axisLine={false} />
              <Tooltip 
                contentStyle={{ background: 'var(--surface)', border: '1px solid var(--border)', borderRadius: '8px' }}
              />
              <Line 
                type="monotone" 
                dataKey="score" 
                stroke="var(--primary)" 
                strokeWidth={3} 
                dot={{ fill: 'var(--primary)', strokeWidth: 2, r: 4 }}
                activeDot={{ r: 6, strokeWidth: 0 }}
              />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </div>

      <div className="glass card">
        <h3 style={{ marginBottom: '1.5rem' }}>Recent Activity</h3>
        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
          <thead>
            <tr style={{ textAlign: 'left', borderBottom: '1px solid var(--border)' }}>
              <th style={{ padding: '1rem', color: 'var(--text-muted)', fontWeight: 500, fontSize: '0.875rem' }}>Student</th>
              <th style={{ padding: '1rem', color: 'var(--text-muted)', fontWeight: 500, fontSize: '0.875rem' }}>Course</th>
              <th style={{ padding: '1rem', color: 'var(--text-muted)', fontWeight: 500, fontSize: '0.875rem' }}>Status</th>
              <th style={{ padding: '1rem', color: 'var(--text-muted)', fontWeight: 500, fontSize: '0.875rem' }}>Action</th>
            </tr>
          </thead>
          <tbody>
            {[
              { id: 1, name: 'Alice Johnson', course: 'Computer Science', status: 'High Risk', color: 'var(--danger)' },
              { id: 2, name: 'Bob Smith', course: 'Physics', status: 'In Review', color: 'var(--warning)' },
              { id: 3, name: 'Charlie Brown', course: 'Mathematics', status: 'Improved', color: 'var(--success)' },
            ].map((row, i) => (
              <tr key={i} style={{ borderBottom: '1px solid var(--border)' }}>
                <td style={{ padding: '1rem', fontWeight: 600 }}>{row.name}</td>
                <td style={{ padding: '1rem', color: 'var(--text-muted)' }}>{row.course}</td>
                <td style={{ padding: '1rem' }}>
                  <span style={{ padding: '0.25rem 0.75rem', borderRadius: '100px', fontSize: '0.75rem', fontWeight: 600, background: `rgba(${row.color === 'var(--danger)' ? '239, 68, 68' : row.color === 'var(--warning)' ? '245, 158, 11' : '16, 185, 129'}, 0.1)`, color: row.color }}>
                    {row.status}
                  </span>
                </td>
                <td style={{ padding: '1rem', display: 'flex', gap: '0.5rem' }}>
                  <button className="btn glass" style={{ padding: '0.4rem 0.8rem', fontSize: '0.75rem' }}>View</button>
                  <button 
                    className="btn glass" 
                    style={{ padding: '0.4rem 0.8rem', fontSize: '0.75rem', color: 'var(--primary)' }}
                    onClick={() => window.open(`http://localhost:8080/api/reports/student/${row.id}`, '_blank')}
                  >
                    <Download size={14} />
                    Report
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Dashboard;
