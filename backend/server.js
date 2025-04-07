import express from 'express';
import cors from 'cors';

const app = express();
app.use(cors());
app.use(express.json());

// Test endpoint
app.get('/api/health', (req, res) => {
  res.send('✅ Backend is still running – updated at fxzdsaf ' + new Date().toLocaleString());

});

app.get('/', (req, res) => {
  res.send('Backend is alive 🚀');
});


const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`🚀 Server is running on port ${PORT}`);
});
