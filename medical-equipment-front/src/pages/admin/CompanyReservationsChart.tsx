// src/components/SalesChart.tsx
import React from 'react';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

// Register components for Chart.js
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

// Define the type for the data prop
interface Data {
  key: string;
  value: number;
}

interface DataProps {
  data: Data[];
}

const CompanyReservationsChart: React.FC<DataProps> = ({ data }) => {
  const chartData = {
    labels: data.map(item => item.key),
    datasets: [
      {
        label: 'Average company rate',
        data: data.map(item => item.value),
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1,
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top' as const,
      },
      title: {
        display: true,
        text: 'Average rate',
      },
    },
  };

  return <Bar data={chartData} options={options} />;
};

export default CompanyReservationsChart;
