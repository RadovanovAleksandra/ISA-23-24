import { useContext, useEffect, useState } from "react";
import { Card, Container, Form, Row, Spinner } from "react-bootstrap";
import axios from "axios";
import { AuthContext } from "../services/AuthProvider";
import QRCode from "qrcode.react";

function SuccessfulReservationsHistoryQrCodes() {
      const [reservations, setReservations] = useState<any[]>([]);
      const [loading, setLoading] = useState(true);
      const [filteringOption, setFilteringOption] = useState('PENDING');

      const authContext = useContext(AuthContext);

      async function fetchComplaints(status:any) {
        try {
            const response = await axios.get<any[]>(process.env.REACT_APP_API_URL + `api/reservations?status=${status}`, 
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

            await fetchComplaints(filteringOption);
    
            setLoading(false)
        }
    
        fetchData();
      }, [authContext?.user?.token, filteringOption]);

      const handleSelectedOptionChange = (e:any) => {
        setFilteringOption(e.target.value);
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

              <h1>Reservations history</h1>

              <Form>
                <Form.Group controlId="selectCompanyOption">
                  <Form.Label>Select company:</Form.Label>
                  <Form.Select
                      value={filteringOption}
                      onChange={handleSelectedOptionChange}>
                  
                      <option key={0} value={'PENDING'}>NEW</option>
                      <option key={1} value={'ACCEPTED'}>ACCEPTED</option>
                      <option key={2} value={'REJECTED'}>REJECTED</option>
                  </Form.Select>
                  </Form.Group>
                </Form>
       
              <Row>
              {reservations.map((reservation:any) => (
                <div key={reservation.id}>
                  <Card style={{ width: '18rem' }}>
                    <Card.Body>
                      <Card.Title>{reservation.companyName} {'at'} {new Date(reservation.createdAt).toLocaleDateString('sr-RS')} {new Date(reservation.createdAt).toLocaleTimeString('sr-RS')}</Card.Title>
                      <Card.Text>
                      <QRCode 
                          value={`${process.env.REACT_APP_API_URL}reservations/${reservation.id}`} 
                          size={256} 
                          bgColor="#ffffff" 
                          fgColor="#000000" 
                          level="Q" 
                        />  
                      </Card.Text>
                    </Card.Body>
                  </Card>
                </div>
              ))}
              </Row>
        </Container>
      );
}

export default SuccessfulReservationsHistoryQrCodes;