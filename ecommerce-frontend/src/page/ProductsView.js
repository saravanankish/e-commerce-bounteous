import {
    Container,
    Typography,
    Button,
    Table,
    TableContainer,
    TableRow,
    TableCell,
    TableHead,
    TableBody,
    IconButton,
    TableSortLabel,
    Box,
    Rating,
    TablePagination,
    TextField,
    InputAdornment
} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import { visuallyHidden } from '@mui/utils';
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import axios from "axios";
import { backendUrl } from "../config";
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { useNavigate } from "react-router-dom";
import { useSnackbar } from "notistack";
import PropTypes from 'prop-types';

function descendingComparator(a, b, orderBy) {
    if (b[orderBy] < a[orderBy]) {
        return -1;
    }
    if (b[orderBy] > a[orderBy]) {
        return 1;
    }
    return 0;
}

function getComparator(order, orderBy) {
    return order === 'desc'
        ? (a, b) => descendingComparator(a, b, orderBy)
        : (a, b) => -descendingComparator(a, b, orderBy);
}

function stableSort(array, comparator) {
    const stabilizedThis = array.map((el, index) => [el, index]);
    stabilizedThis.sort((a, b) => {
        const order = comparator(a[0], b[0]);
        if (order !== 0) {
            return order;
        }
        return a[1] - b[1];
    });
    return stabilizedThis.map((el) => el[0]);
}

const headCells = [
    {
        id: 'image',
        numeric: false,
        disablePadding: false,
        label: 'Image',
    },
    {
        id: 'name',
        numeric: false,
        disablePadding: false,
        label: 'Product Name',
    },
    {
        id: 'category',
        numeric: false,
        disablePadding: false,
        label: 'Category',
    },
    {
        id: 'rating',
        numeric: true,
        disablePadding: false,
        label: 'Rating',
    },
    {
        id: 'cost',
        numeric: true,
        disablePadding: false,
        label: 'Cost',
    },
];

function EnhancedTableHead(props) {
    const { order, orderBy, onRequestSort } = props;

    const createSortHandler = (property) => (event) => {
        onRequestSort(event, property);
    };

    return (
        <TableHead>
            <TableRow>
                {headCells.map((headCell) => (
                    <TableCell
                        key={headCell.id}
                        sortDirection={orderBy === headCell.id ? order : false}
                    >
                        <TableSortLabel
                            active={orderBy === headCell.id}
                            direction={orderBy === headCell.id ? order : 'asc'}
                            onClick={createSortHandler(headCell.id)}
                        >
                            {headCell.label}
                            {orderBy === headCell.id ? (
                                <Box component="span" sx={visuallyHidden}>
                                    {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                                </Box>
                            ) : null}
                        </TableSortLabel>
                    </TableCell>
                ))}
                <TableCell align="center">
                    Actions
                </TableCell>
            </TableRow>
        </TableHead>
    );
}
EnhancedTableHead.propTypes = {
    numSelected: PropTypes.number.isRequired,
    onRequestSort: PropTypes.func.isRequired,
    onSelectAllClick: PropTypes.func.isRequired,
    order: PropTypes.oneOf(['asc', 'desc']).isRequired,
    orderBy: PropTypes.string.isRequired,
    rowCount: PropTypes.number.isRequired,
};

const ProductsView = () => {

    const role = useSelector(state => state.login.role);
    const navigate = useNavigate();
    const [products, setProducts] = useState([]);
    const [search, setSearch] = useState("");
    const [order, setOrder] = useState('asc');
    const [orderBy, setOrderBy] = useState('calories');
    const [selected, setSelected] = useState([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const { enqueueSnackbar } = useSnackbar();
    const [totalProducts, setTotalProducts] = useState(1);
    const [debounceTime, setDebounceTime] = useState(null);

    useEffect(() => {
        if (!search)
            fetchProducts(rowsPerPage, page);
        else
            fetchProducts(rowsPerPage, page, search)
        // eslint-disable-next-line
    }, [page, rowsPerPage])

    useEffect(() => {
        if (search)
            debounce(fetchProducts, 1000)(rowsPerPage, page, search);
        // eslint-disable-next-line
    }, [search])

    const debounce = (func, delay) => {
        let debounceTimer = debounceTime
        return function (limit, page, search) {
            clearTimeout(debounceTimer)
            debounceTimer
                = setTimeout(() => func(limit, page, search), delay)
            setDebounceTime(debounceTimer)
        }
    }

    const fetchProducts = async (limit, page, search) => {
        axios.get(`${backendUrl}/products?limit=${limit}&page=${page}&search=${search || ""}`).then(res => {
            setProducts(res.data.products);
            setTotalProducts(res.data.total);
        }).catch(err => {
            enqueueSnackbar('Unable to fetch products', { variant: 'error' })
        })
    }

    const handleRequestSort = (event, property) => {
        const isAsc = orderBy === property && order === 'asc';
        setOrder(isAsc ? 'desc' : 'asc');
        setOrderBy(property);
    };

    const handleSelectAllClick = (event) => {
        if (event.target.checked) {
            const newSelecteds = products.map((n) => n.productId);
            setSelected(newSelecteds);
            return;
        }
        setSelected([]);
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    return (
        <Container sx={{ mt: 1, mx: 3, mb: 3 }} >
            <div style={{ display: "flex", justifyContent: "space-between", paddingTop: "15px" }}>
                <Typography variant="h4" color="primary">Products</Typography>
                {
                    role === "ADMIN" &&
                    <Button variant="contained" onClick={() => navigate("/admin/add/product")}>Add Product</Button>
                }
            </div>
            <TextField
                sx={{ mt: 1 }}
                fullWidth
                label="Search Product"
                placeholder="Search"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                InputProps={{
                    startAdornment: (
                        <InputAdornment position="start">
                            <SearchIcon />
                        </InputAdornment>
                    ),
                }}
            />
            <TableContainer style={{ border: '1px solid #c9c3ad', borderRadius: "5px", backgroundColor: "white", marginTop: "5px" }} className="tableContainer">
                <Table
                    sx={{ minWidth: 750 }}
                    aria-labelledby="tableTitle"
                    size='medium'
                >
                    <EnhancedTableHead
                        numSelected={selected.length}
                        order={order}
                        orderBy={orderBy}
                        onSelectAllClick={handleSelectAllClick}
                        onRequestSort={handleRequestSort}
                        rowCount={products.length}
                    />
                    <TableBody>
                        {stableSort(products, getComparator(order, orderBy))
                            .map((row, index) => {
                                return (
                                    <TableRow
                                        hover
                                        tabIndex={-1}
                                        key={index}
                                    >
                                        <TableCell>
                                            <img src={row.thumbnail} alt={row.name} width="80" />
                                        </TableCell>
                                        <TableCell
                                            component="th"
                                            scope="row"
                                        >
                                            {row.name}
                                        </TableCell>
                                        <TableCell>{row.category}</TableCell>
                                        <TableCell>
                                            <Box
                                                sx={{
                                                    width: 200,
                                                    display: 'flex',
                                                    alignItems: 'center',
                                                }}
                                            >
                                                <Rating value={row.rating} readOnly precision={0.5} />
                                                <Box sx={{ ml: 1 }}>{row.rating}</Box>
                                            </Box>
                                        </TableCell>
                                        <TableCell>{row.price}</TableCell>
                                        <TableCell align="center">
                                            <IconButton sx={{ mr: 2 }} color="primary" onClick={() => navigate("/admin/edit/product/" + row.productId)} >
                                                <EditIcon />
                                            </IconButton>
                                            <IconButton color="error">
                                                <DeleteIcon />
                                            </IconButton>
                                        </TableCell>
                                    </TableRow>
                                );
                            })}
                    </TableBody>
                </Table>
                <TablePagination
                    className="tableContainer"
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={totalProducts}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </TableContainer>
        </Container>
    )
}

export default ProductsView;