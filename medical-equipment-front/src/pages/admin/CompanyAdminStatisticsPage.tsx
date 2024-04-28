import axios from 'axios';
import React, { useState, useEffect, useContext } from 'react';
import { Container, Form, Row, Spinner } from 'react-bootstrap';
import NumberOfTermsChart from './NumberOfTermsChart';
import CompanyReservationsChart from './CompanyReservationsChart';
import ReactDatePicker from 'react-datepicker';
import { AuthContext } from '../../services/AuthProvider';

function CompanyAdminStatisticsPage() {
  const [loading, setLoading] = useState(true);
  const [selectedTermOption, setSelectedTermOption] = useState(0);
  const [selectedReservationOption, setSelectedReservationOption] = useState(0);
  const [dateStart, setDateStart] = useState<Date | null>(null);
  const [dateEnd, setDateEnd] = useState<Date | null>(null);

  const [averageRate, setAverageRate] = useState(0);
  const [totalIncome, setTotalIncome] = useState(0);
  const [termsData, setTermsData] = useState([])
  const [reservationsData, setReservationsData] = useState([])

  const authContext = useContext(AuthContext);

  const fetchData = async () => {

    setLoading(true)

    await fetchAverageRate();

    setLoading(false)
  }

  const fetchAverageRate = async () => {

    try {
        const response = await axios.get(process.env.REACT_APP_API_URL + `api/statistics/average-rate`,
        {headers: {Authorization: `Bearer ${authContext?.user?.token}`}}
        );

        setAverageRate(response.data.averageRate);
        
    } catch (error) {
        console.log(error)                
    }
  }

  const fetchTotalIncome = async () => {

    try {
        const response = await axios.get(process.env.REACT_APP_API_URL + `api/statistics/total-income?start=${dateStart?.toISOString().split('T')[0]}&end=${dateEnd?.toISOString().split('T')[0]}`,
        {headers: {Authorization: `Bearer ${authContext?.user?.token}`}}
        );

        setTotalIncome(response.data.totalIncome);
        
    } catch (error) {
        console.log(error)                
    }
  }

  const fetchTermsStatistics = async () => {

    try {
        let criteria = '';
        if (selectedTermOption === 0) {
            criteria = 'MONTH';
        } else if (selectedTermOption === 1) {
            criteria = 'QUARTAL';
        } else {
            criteria = 'YEAR';
        }
        const response = await axios.get(process.env.REACT_APP_API_URL + `api/statistics/number-of-terms?criteria=${criteria}`,
        {headers: {Authorization: `Bearer ${authContext?.user?.token}`}}
        );
        
        setTermsData(response.data);
        
    } catch (error) {
        console.log(error)                
    }
  }

  const fetchReservationsStatistics = async () => {

    try {
        let criteria = '';
        if (selectedReservationOption === 0) {
            criteria = 'MONTH';
        } else if (selectedReservationOption === 1) {
            criteria = 'QUARTAL';
        } else {
            criteria = 'YEAR';
        }
        const response = await axios.get(process.env.REACT_APP_API_URL + `api/statistics/number-of-reservations?criteria=${criteria}`,
        {headers: {Authorization: `Bearer ${authContext?.user?.token}`}}
        );
        
        setReservationsData(response.data);
        
    } catch (error) {
        console.log(error)                
    }
  }

  useEffect(() => {
    if (authContext?.user?.token) {
        fetchData();
    }
  }, [authContext?.user?.token]);

  useEffect(() => {
    if (dateStart && dateEnd) {
        fetchTotalIncome();
    }
  }, [dateStart, dateEnd]);

  useEffect(() => {
    if (authContext?.user?.token) {
        fetchTermsStatistics()
    }
  }, [selectedTermOption, authContext?.user?.token  ]);

  useEffect(() => {
    if (authContext?.user?.token) {
        fetchReservationsStatistics()
    }
  }, [selectedReservationOption, authContext?.user?.token  ]);

  const handleSelectedTermChartOptionChange = async (e:any) => {
    setSelectedTermOption(+e.target.value);
  };

  const handleSelectedReservationsChartOptionChange = async (e:any) => {
    setSelectedReservationOption(+e.target.value);
  };

  function changeStartDate(value:any) {
    setDateStart(value)
  }

  function changeEndDate(value:any) {
    setDateEnd(value)
  }

  return (
    <div>
    {loading && 
        <Container className="mt-3">
        <Row className="justify-content-center">
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </Row>
        </Container>}

        <Container className='mt-3'>
            <Row>
                <h1>Average rate</h1>
                <span className="badge rounded-pill text-bg-danger w-25">{averageRate}</span>
                <h1>Number of terms</h1>
                <Form>
                <Form.Group controlId="selectTerm">
                  <Form.Label>Select range:</Form.Label>
                  <Form.Select
                      value={selectedTermOption}
                      onChange={handleSelectedTermChartOptionChange}>
                        <option key={0} value={0}>Month</option>
                        <option key={1} value={1}>Quartal</option>
                        <option key={2} value={2}>Year</option>
                  </Form.Select>
                  </Form.Group>
                </Form>
                <NumberOfTermsChart data={termsData} />
            <h1>Number of reservations</h1>
            <Form>
                <Form.Group controlId="selectTerm">
                  <Form.Label>Select range:</Form.Label>
                  <Form.Select
                      value={selectedReservationOption}
                      onChange={handleSelectedReservationsChartOptionChange}>
                        <option key={0} value={0}>Month</option>
                        <option key={1} value={1}>Quartal</option>
                        <option key={2} value={2}>Year</option>
                  </Form.Select>
                  </Form.Group>
                </Form>
            <CompanyReservationsChart data={reservationsData} />

                <h1>Income in selected period</h1>
                <Form>
                <Form.Group controlId="selectTerm">
                  <Form.Label>Select date range:</Form.Label>
                  <ReactDatePicker
                        selected={dateStart}
                        onChange={changeStartDate}
                        className="form-control"
                    />
                    <Form.Label>to:</Form.Label>
                    <ReactDatePicker
                        selected={dateEnd}
                        onChange={changeEndDate}
                        className="form-control"
                    />
                  </Form.Group>
                </Form>
                <br />
                <br />
                <span className="badge rounded-pill text-bg-danger w-25">{totalIncome}</span>
            </Row>
        </Container>
        <br />
        <br />
        <br />
    </div>
  );
}

export default CompanyAdminStatisticsPage;