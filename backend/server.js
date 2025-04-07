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
  queueLimit: 0
});

console.log('✅ MySQL connection pool initializedr.');










const app = express();
app.use(cors());
app.use(express.json());

// Test endpoint
app.get('/api/health', (req, res) => {
  res.send('✅ Backend is still running sss– updated at fxzdsaf ' + new Date().toLocaleString());

});

app.get('/', (req, res) => {
  res.send('Backend is aldsdive 🚀');
});



app.get('/test-db', (req, res) => {
  db.query('SELECT NOW()', (err, results) => {
    if (err) {
      console.error('❌ DB query failed:', err.message);
      return res.status(500).send('DB errord: ' + err.message);
    }
    res.send('✅ DB Time: ' + results[0]['NOW()']);
  });
});



const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`🚀 Server is running on port ${PORT}`);
});



