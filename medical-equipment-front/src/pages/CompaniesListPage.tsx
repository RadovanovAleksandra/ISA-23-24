import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { Table, InputGroup, FormControl, Container, Row, Spinner, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function CompaniesListPage() {
  const [companies, setCompanies] = useState([]);
  const [filter, setFilter] = useState('');
  const [sortConfig, setSortConfig] = useState({ key: 'name', direction: 'ascending' });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCompanies = async () => {
        try {
            const response = await axios.get(process.env.REACT_APP_API_URL + `api/companies`);
            setCompanies(response.data)
            setLoading(false)
        } catch (error) {
            console.log(error)                
        }

        setLoading(false)
    }

    fetchCompanies();
  }, []);
 
  const filteredCompanies = companies.filter((company:any) =>
    company.name.toLowerCase().includes(filter.toLowerCase()) || 
    company.address.toLowerCase().includes(filter.toLowerCase()) ||
    company.city.toLowerCase().includes(filter.toLowerCase())
  );

  const sortedComanies = [...filteredCompanies].sort((a, b) => {
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
            <InputGroup className="mb-3">
                <FormControl
                    placeholder="Filter by company name, city, or address"
                    value={filter}
                    onChange={e => setFilter(e.target.value)}
                    />
            </InputGroup>

            <Table striped bordered hover>
                <thead>
                <tr>
                    <th onClick={() => requestSort('name')}>
                    Name {sortConfig.key === 'name' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                    <th onClick={() => requestSort('city')}>
                    City {sortConfig.key === 'city' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                    <th onClick={() => requestSort('address')}>
                    Address {sortConfig.key === 'address' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                    <th onClick={() => requestSort('rating')}>
                    Rating {sortConfig.key === 'rating' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                    <th onClick={() => requestSort('wokringHoursStart')}>
                    Working hours start {sortConfig.key === 'wokringHoursStart' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                    <th onClick={() => requestSort('wokringHoursEnd')}>
                    Working hours end {sortConfig.key === 'wokringHoursEnd' ? (sortConfig.direction === 'ascending' ? '↑' : '↓') : null}
                    </th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {sortedComanies.map((company:any) => (
                    <tr key={company.id}>
                    <td>{company.name}</td>
                    <td>{company.city}</td>
                    <td>{company.address}</td>
                    <td>{company.rating}</td>
                    <td>{company.workingHoursStart}</td>
                    <td>{company.workingHoursEnd}</td>
                    <td><Link className='btn btn-warning' to={`/companies/${company.id}/catalog`}>See catalog</Link></td>
                    </tr>
                ))}
                </tbody>
            </Table>
            </Row>
        </Container>
      
    </div>
  );
}

export default CompaniesListPage;