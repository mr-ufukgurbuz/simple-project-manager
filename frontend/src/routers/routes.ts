import IRoute from '../interfaces/IRoute';
import { ROUTES } from '../constants/constants';
import AboutPage from '../pages/about';
import HomePage from '../pages/home';

const routes: IRoute[] = [
    {
        path: ROUTES.HOME,
        name: 'Home Page',
        component: HomePage,
        exact: true
    },
    {
        path: ROUTES.ABOUT,
        name: 'About Page',
        component: AboutPage,
        exact: true
    },
    {
        path: ROUTES.ABOUT + ':number',
        name: 'About Page',
        component: AboutPage,
        exact: true
    },
]

export default routes;
