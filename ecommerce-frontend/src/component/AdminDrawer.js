import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import Typography from '@mui/material/Typography';
import ListItemText from '@mui/material/ListItemText';
import ListItemIcon from '@mui/material/ListItemIcon';
import { useSelector } from "react-redux";
import AddBoxIcon from '@mui/icons-material/AddBox';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import SupervisedUserCircleIcon from '@mui/icons-material/SupervisedUserCircle';
import InventoryIcon from '@mui/icons-material/Inventory';
import CategoryIcon from '@mui/icons-material/Category';
import ControlPointDuplicateIcon from '@mui/icons-material/ControlPointDuplicate';
import SellIcon from '@mui/icons-material/Sell';
import { ListSubheader } from '@mui/material';
import { useNavigate } from "react-router-dom";
import { Category } from '@mui/icons-material';

const links = [
    {
        title: "View",
        role: "ALL",
        items: [
            {
                name: "Products",
                icon: <InventoryIcon />,
                id: "products",
                navigate: "/admin/products",
                role: "ALL"
            },
            {
                name: "Customers",
                icon: <SupervisedUserCircleIcon />,
                id: "customer",
                navigate: "/admin/customers",
                role: "ALL"
            },
            {
                name: "Brands",
                icon: <SellIcon />,
                id: "brands",
                navigate: "/admin/brands",
                role: "ALL"
            },
            {
                name: "Category",
                icon: <CategoryIcon />,
                id: Category,
                navigate: "/admin/category",
                role: "ALL"
            }
        ]
    },
    {
        title: "Add data",
        role: "ADMIN",
        items: [
            {
                name: "Add Product",
                icon: <AddBoxIcon />,
                id: "addProduct",
                navigate: "/admin/add/product",
                role: "ADMIN"
            },
            {
                name: "Add Customer",
                icon: <PersonAddIcon />,
                id: "addCustomer",
                navigate: "/admin/add/customer",
                role: "ADMIN"
            },
            {
                name: "Add Brand",
                icon: <SellIcon />,
                id: "addBrand",
                navigate: "/admin/add/brand",
                role: "ADMIN"
            },
            {
                name: "Add category",
                icon: <ControlPointDuplicateIcon />,
                id: "addCategory",
                navigate: "/admin/add/category",
                role: "ADMIN"
            }
        ]
    },


]

const AdminDrawer = ({ open, setOpen }) => {

    const role = useSelector(state => state.login.role);
    const navigate = useNavigate();

    const handleDrawerToggle = () => {
        setOpen(prev => !prev);
    };

    return (
        <Drawer
            anchor="left"
            variant="temporary"
            open={open}
            onClose={handleDrawerToggle}
            sx={{
                display: { xs: 'block' },
                '& .MuiDrawer-paper': { boxSizing: 'border-box', width: 250 },
            }}
        >
            <Box
                sx={{ width: 250 }}
                role="presentation"
                onClick={handleDrawerToggle}
                onKeyDown={handleDrawerToggle}
            >
                <Typography variant="h6" sx={{ my: 2, marginLeft: "20px" }}>
                    {role}
                </Typography>
                <Divider />
                {
                    links.map(link => (
                        (link.role === "ALL" || link.role === role) && (
                            <List subheader={<ListSubheader>{link.title}</ListSubheader>}>
                                {
                                    link.items.map(ele => (
                                        (ele.role === "ALL" || ele.role === role) && (
                                            <ListItem key={ele.id} disablePadding onClick={() => navigate(ele.navigate)}>
                                                <ListItemButton>
                                                    <ListItemIcon>
                                                        {ele.icon}
                                                    </ListItemIcon>
                                                    <ListItemText primary={ele.name} />
                                                </ListItemButton>
                                            </ListItem>
                                        ))
                                    )
                                }
                            </List>
                        )
                    ))
                }
            </Box>
        </Drawer>
    )

}

export default AdminDrawer;