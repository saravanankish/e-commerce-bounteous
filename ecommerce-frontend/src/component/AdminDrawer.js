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
import { useNavigate } from "react-router-dom";

const links = [
    {
        name: "Add Product",
        icon: <AddBoxIcon />,
        id: "addProduct",
        navigate: "/admin/add-product",
        role: "ADMIN"
    },
    {
        name: "Add Customer",
        icon: <PersonAddIcon />,
        id: "addCustomer",
        navigate: "/admin/add-customer",
        role: "ADMIN"
    },
    {
        name: "Customers",
        icon: <SupervisedUserCircleIcon />,
        id: "customer",
        navigate: "/admin/customers",
        role: "ALL"
    }
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
                <List>
                    {
                        links.map(ele => (

                            (ele.role === "ALL" || ele.role === role) && (
                                <ListItem key={ele.id} disablePadding onClick={() => navigate(ele.navigate)}>
                                    <ListItemButton>
                                        <ListItemIcon>
                                            {ele.icon}
                                        </ListItemIcon>
                                        <ListItemText primary={ele.name} />
                                    </ListItemButton>
                                </ListItem>
                            )
                        ))
                    }
                </List>
            </Box>
        </Drawer>
    )

}

export default AdminDrawer;