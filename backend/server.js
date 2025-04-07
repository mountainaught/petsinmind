import express from 'express';
import cors from 'cors';
import mysql from 'mysql2';

const app = express();
app.use(cors());
app.use(express.json());

// 1) Create a MySQL connection pool
const db = mysql.createPool({
  host: process.env.DB_HOST || 'mysql', // Points to MySQL service name in OKD
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0,
});

// 2) A health route to check the app is alive
app.get('/api/health', (req, res) => {
  res.send('✅ App is running at ' + new Date().toLocaleString());
});

// 3) A route to CREATE/WRITE data
app.post('/api/add-item', (req, res) => {
  const { name } = req.body; // e.g. { "name": "Apple" }
  if (!name) return res.status(400).send('Item name required');

  const query = 'INSERT INTO items (name) VALUES (?)';
  db.query(query, [name], (err, results) => {
    if (err) {
      console.error('❌ Insert failed:', err.message);
      return res.status(500).send('DB error: ' + err.message);
    }
    res.json({ message: '✅ Item added', insertId: results.insertId });
  });
});

// 4) A route to READ data
app.get('/api/items', (req, res) => {
  db.query('SELECT * FROM items', (err, results) => {
    if (err) {
      console.error('❌ Query failed:', err.message);
      return res.status(500).send('DB error: ' + err.message);
    }
    res.json(results);
  });
});

// 5) Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log('🚀 Server running on port ' + PORT);
});
