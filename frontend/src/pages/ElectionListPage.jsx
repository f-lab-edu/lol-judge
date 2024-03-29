import * as React from "react";
import { styled } from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { Button, Pagination } from "@mui/material";
import { Link } from "react-router-dom";
import axios from "axios";
import { convertUrl } from "../utils/urlUtil";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    color: theme.palette.common.black,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

export default function ElectionListPage() {
  const [pageNumber, setPageNumber] = React.useState(1);
  const [electionList, setElectionList] = React.useState([]);
  const [electionSize, setElectionSize] = React.useState(0);
  const pageSize = 10;

  const handleChangePage = (event, page) => {
    setPageNumber(page);
  };

  React.useEffect(() => {
    axios
      .get(convertUrl("/elections"), {
        params: {
          pageNumber: pageNumber - 1,
          pageSize: pageSize,
          status: "IN_PROGRESS",
        },
      })
      .catch((e) => console.error(e))
      .then((res) => res?.data)
      .then((payload) => payload?.data)
      .then((data) => {
        setElectionList(data?.electionInfoDtoList);
        setElectionSize(data?.entireSize);
      });
  }, [pageNumber]);

  return (
    <div>
      <TableContainer component={Paper} className="mt-10">
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell align="left">썸네일</StyledTableCell>
              <StyledTableCell align="center">제목</StyledTableCell>
              <StyledTableCell align="center">투표수</StyledTableCell>
              <StyledTableCell align="center">작성자</StyledTableCell>
              <StyledTableCell align="center">날짜</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {electionList?.map((e) => (
              <StyledTableRow key={e.id} component={Link} to={`elections/${e.id}`}>
                <StyledTableCell align="left" scope="row">
                  <img src={e.thumbnail} style={{ height: 80, width: 80 }} />
                </StyledTableCell>
                <StyledTableCell align="center">
                  {e.title}
                </StyledTableCell>
                <StyledTableCell align="center">
                  {e.totalVotedCount}
                </StyledTableCell>
                <StyledTableCell align="center">
                  {e.writer}
                </StyledTableCell>
                <StyledTableCell align="center">
                  {e.createdAt}
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <div className="flex justify-between pt-3">
        <Pagination
          count={Math.ceil(electionSize / pageSize)}
          onChange={handleChangePage}
        />
        <Link to="/elections/register">
          <Button variant="contained" className="p-3">
            등록하기
          </Button>
        </Link>
      </div>
    </div>
  );
}
