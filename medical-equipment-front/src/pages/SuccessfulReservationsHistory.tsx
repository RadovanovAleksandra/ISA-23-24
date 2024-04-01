import { useContext, useEffect, useState } from "react";
import { Container, Row, Spinner, Table } from "react-bootstrap";
import axios from "axios";
import { AuthContext } from "../services/AuthProvider";

function SuccessfulReservationsHistory() {
      const [reservations, setReservations] = useState<any[]>([]);
      const [loading, setLoading] = useState(true);
      const [sortConfig, setSortConfig] = useState({ key: 'name', direction: 'ascending' });

      const authContext = useContext(AuthContext);

      async function fetchComplaints() {
        try {
            const response = await axios.get<any[]>(process.env.REACT_APP_API_URL + `api/reservations/successful`, 
            {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
            setReservations(response.data);
        } catch (error) {
            console.log(error)                
        }
      }

      useEffect(() => {
        const fetchData = async () => {
            setLoading(true);

            if (!authContext?.user?.token) {
                return;
            }

            await fetchComplaints();
    
            setLoading(false)
        }
    
        fetchData();
      }, [authContext?.user?.token]);

      [...reservations].sort((a, b) => {
        if (a[sortConfig.key] < b[sortConfig.key]) {
          return sortConfig.direction === 'ascending' ? -1 : 1;
        }
        if (a[sortConfig.key] > b[sortConfig.key]) {
          return sortConfig.direction === 'ascending' ? 1 : -1;
        }
        return 0;
      });
    
      const requestSort = (key:any) => {
        let direction = 'ascending';
        if (
          sortConfig.key === key &&
          sortConfig.direction === 'ascending'
        ) {
          direction = 'descending';
        }
        setSortConfig({ key, direction });
      };
    
      return (
        <Container>
            {loading && 
                <Container className="mt-3">
                    <Row className="justify-content-center">
                        <Spinner animation="border" role="status">
                            <span className="visually-hidden">Loading...</span>
                        </Spinner>
                    </Row>
                </Container>}
          <h1>Succesful reservations history</h1>
          <Table striped bordered hover>
                <thead>
                <tr>
                    <th onClick={() => requestSort('companyName')}>
                    Company {sortConfig.key === 'companyName' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                    <th onClick={() => requestSort('createdAt')}>
                    Created At {sortConfig.key === 'createdAt' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                    <th onClick={() => requestSort('termStart')}>
                    Starts At {sortConfig.key === 'termStart' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                    <th onClick={() => requestSort('duration')}>
                    Duration {sortConfig.key === 'duration' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                    <th onClick={() => requestSort('price')}>
                    Price {sortConfig.key === 'price' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                </tr>
                </thead>
                <tbody>
                {reservations.map((reservation:any) => (
                    <tr key={reservation.id}>
                        <td>{reservation.companyName}</td>
                        <td>{new Date(reservation.createdAt).toLocaleDateString('sr-RS')} {new Date(reservation.createdAt).toLocaleTimeString('sr-RS')}</td>
                        <td>{new Date(reservation.termStart).toLocaleDateString('sr-RS')} {new Date(reservation.termStart).toLocaleTimeString('sr-RS')}</td>
                        <td>{reservation.duration}</td>
                        <td>{reservation.price}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </Container>
      );
}

export default SuccessfulReservationsHistory;