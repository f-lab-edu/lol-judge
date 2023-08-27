import * as React from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import PaginationOutlined from '../components/PaginationOutlined';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    color: theme.palette.common.black,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

function createData(name, calories, fat, carbs, protein) {
  return { name, calories, fat, carbs, protein };
}

const rows = [
  createData('Frozen yoghurt', 159, 6.0, 'user123', '2022-01-13'),
  createData('Ice cream sandwich', 237, 9.0, 'user123', '2022-01-13'),
  createData('Eclair', 262, 16.0, 'user123', '2022-01-13'),
  createData('Cupcake', 305, 3.7, 'user1234','2022-01-12'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
  createData('Gingerbread', 356, 16.0, 'admin1233','2022-01-11'),
];

export default function ElectionList() {
  return (
    <div>
        <TableContainer component={Paper} className='mt-10'>
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
            <TableHead>
            <TableRow>
                <StyledTableCell>제목</StyledTableCell>
                <StyledTableCell align="right">티어</StyledTableCell>
                <StyledTableCell align="right">투표수</StyledTableCell>
                <StyledTableCell align="right">작성자</StyledTableCell>
                <StyledTableCell align="right">날짜</StyledTableCell>
            </TableRow>
            </TableHead>
            <TableBody>
            {rows.map((row) => (
                <StyledTableRow key={row.name}>
                <StyledTableCell component="th" scope="row">
                    {row.name}
                </StyledTableCell>
                <StyledTableCell align="right">{row.calories}</StyledTableCell>
                <StyledTableCell align="right">{row.fat}</StyledTableCell>
                <StyledTableCell align="right">{row.carbs}</StyledTableCell>
                <StyledTableCell align="right">{row.protein}</StyledTableCell>
                </StyledTableRow>
            ))}
            </TableBody>
        </Table>
        </TableContainer>
        <PaginationOutlined/>
    </div>
  );
}