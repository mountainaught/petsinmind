import express from 'express';
import cors from 'cors';
import mysql from 'mysql2';

const db = mysql.createPool({
  host: process.env.DB_HOST || 'mysql',
  user: process.env.MYSQL_USER,
  password: process.env.MYSQL_PASSWORD,
  database: process.env.MYSQL_DATABASE,
  port: 3306,
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0,
});

console.log('✅ MySQL connection pool initialized.');

const app = express();
app.use(cors());
app.use(express.json());

app.get('/', (req, res) => {
  res.send('✅ Backend is alive');
});

app.get('/api/health', (req, res) => {
  res.send('✅ Healath check – ' + new Date().toLocaleString());
});

app.get('/api/db-check', (req, res) => {
  db.query('SHOW TABLES', (err, results) => {
    if (err) {
      console.error('❌ DB check failed:', err.message);
      return res.status(500).send('❌ DB error: ' + err.message);
    }
    res.json({
      message: '✅ DB Connected',
      tables: results,
    });
  });
});

app.get('/test-db', (req, res) => {
  db.query('SELECT NOW()', (err, results) => {
    if (err) {
      console.error('❌ DB query failed:', err.message);
      return res.status(500).send('❌ DB error: ' + err.message);
    }
    res.send('✅ DB Time: ' + results[0]['NOW()']);
  });
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`🚀 Server is running on port ${PORT}`);
});
