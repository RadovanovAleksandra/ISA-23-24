import { useContext, useEffect, useState } from "react";
import { Alert, Button, Card, Container, Form, Row, Spinner } from "react-bootstrap";
import axios from "axios";
import { AuthContext } from "../services/AuthProvider";
import { useNavigate, useParams } from "react-router-dom";
import ReactDatePicker from "react-datepicker";

function CompanyCatalogPage() {
      const [products, setProducts] = useState<any[]>([]);
      const [loading, setLoading] = useState(true);
      const [selectedTerm, setSelectedTerm] = useState<any>("");
      const [customTerm, setCustomTerm] = useState<any>(null);
      const [termOptions, setTermOptions] = useState<any[]>([]);
      const [serverError, setServerError] = useState('');
      const [responseSuccess, setResponseSuccess] = useState(false);

      const authContext = useContext(AuthContext);
      const {id} = useParams();
      const navigate = useNavigate();

      async function fetchProducts() {
        try {
            const response = await axios.get<any[]>(process.env.REACT_APP_API_URL + `api/companies/${id}/equipment`);
            setProducts(response.data.map(x => ({...x, selectedQuantity:0})));
        } catch (error) {
            console.log(error)                
        }

        try {
            const response = await axios.get<any[]>(process.env.REACT_APP_API_URL + `api/companies/${id}/terms`);
            setTermOptions(response.data);
        } catch (error) {
            console.log(error)                
        }
      }

      const fetchData = async () => {
            setLoading(true);

            await fetchProducts();

            setLoading(false)
        }

      useEffect(() => {
        fetchData();
      }, []);

      function addForReservation(product: any) {
        const matching = products.find(x => x.id === product.id);
        if (!product.availableQuantity) return;
        if (product.availableQuantity === product.selectedQuantity) return;
        matching.selectedQuantity = matching.selectedQuantity + 1;
        setProducts([...products])
      }
      const handleSelectedTermChange = (e:any) => {
        setSelectedTerm(e.target.value);
      };

      async function confirmReservation() {
        try {
            setLoading(true)
            setServerError('')
            await axios.post<any[]>(process.env.REACT_APP_API_URL + `api/reservations`,
            {
                termId: selectedTerm,
                customTerm, companyId: id,
                reservationItems: products.filter(x => x.selectedQuantity !== 0).map(x => ({
                    equipmentId: x.id,
                    quantity: x.selectedQuantity
                }
            )
            )},
            {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
            
            setResponseSuccess(true)
            setTimeout(() => {
                navigate('/')
                
            }, 500)
        } catch (error:any) {
            console.log(error)                
            if (error.response.status === 400)
                setServerError(error.response.data.message);
            else {
                setServerError("Server error");
            }
        }
        setLoading(false)
      }
    
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

              <h1>Catalog</h1>
              <Row>
              {products.map((product:any) => (
                <div key={product.id} className="col-4 mb-4">
                  <Card style={{ width: '18rem' }}>
                    <Card.Body>
                      <Card.Title>{product.name}</Card.Title>
                      <Card.Text>
                        Available quantity: {product.availableQuantity}
                      </Card.Text>
                      <Card.Text>
                        Price: {product.price}
                      </Card.Text>
                      <Card.Text>
                        {authContext?.user?.token &&
                            <Button className="btn btn-success" disabled={product.selectedQuantity === product.availableQuantity} onClick={() => addForReservation(product)}>Add for reservation</Button>
                        }
                      </Card.Text>
                    </Card.Body>
                  </Card>
                </div>
              ))}
              </Row>

              {products.find(x => x.selectedQuantity > 0) && 
              <>
              <h1>For reservation</h1>
              <Row className="mt-3">
                {products.map((product:any) => (
                    (product.selectedQuantity > 0) && (<div key={product.id}>{product.name} - {product.selectedQuantity} pcs.</div>)
                ))}

                
              <Form>
                <Form.Group controlId="selectTerm">
                  <Form.Label>Select term:</Form.Label>
                  <Form.Select
                      value={selectedTerm}
                      onChange={handleSelectedTermChange}>
                        <option value={""}></option>
                        {termOptions.map((term:any) => (
                            <option key={term.id} value={term.id}>{new Date(term.timestamp).toLocaleDateString('sr-RS')} {new Date(term.timestamp).toLocaleTimeString('sr-RS')}, / duration: {term.durationInMinutes} mins.</option>
                        ))}
                  </Form.Select>
                  </Form.Group>

                  <Form.Group controlId="customTerm" className="my-3">
                    <Form.Label>No terms work for you? Select your own term here:</Form.Label>
                    <ReactDatePicker
                        selected={customTerm}
                        onChange={(date:any) => setCustomTerm(date)}
                        showTimeSelect
                        timeFormat="HH:mm"
                        timeIntervals={15}
                        dateFormat="MMMM d, yyyy h:mm aa"
                        className="form-control"
                    />
                  </Form.Group>

                  <Button variant="primary" onClick={confirmReservation}>Confirm</Button>
                  {responseSuccess && <Alert variant="success">Reservation successful</Alert>}
                  {serverError && <Alert variant="danger">{serverError}</Alert>}
                </Form>
              </Row>
              </>
              }
        </Container>
      );
}

export default CompanyCatalogPage;