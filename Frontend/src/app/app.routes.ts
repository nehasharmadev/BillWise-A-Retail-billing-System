import { RouterModule,Routes } from '@angular/router';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { ExploreComponent } from './Components/explore/explore.component';
import { ItemsComponent } from './Components/items/items.component';
import { CategoryComponent } from './Components/category/category.component';
import { UsersComponent } from './Components/users/users.component';
import { PageNotFoundComponent } from './Components/page-not-found/page-not-found.component';
import { LoginComponent } from './Components/login/login.component';
import { AuthGuard } from './auth.guard';
import { OrderhistoryComponent } from './Components/orderhistory/orderhistory.component';

// export const routes: Routes = [
//     {path:'dashboard', component: DashboardComponent},
//     {path:'explore', component: ExploreComponent},
//     {path:'manage-items', component: ItemsComponent},
//     {path:'manage-categories', component: CategoryComponent},
//     {path:'manage-users', component: UsersComponent},
//     {path:'login',component:LoginComponent},
//     {path:"**", component: PageNotFoundComponent}
// ];
export const routes: Routes = [
  { path: 'dashboard', component: OrderhistoryComponent, canActivate:[AuthGuard]},
  { path: 'explore', component: ExploreComponent, canActivate: [AuthGuard] },
  { path: 'manage-items', component: ItemsComponent, canActivate: [AuthGuard] },
  { path: 'manage-categories', component: CategoryComponent, canActivate: [AuthGuard] },
  { path: 'manage-users', component: UsersComponent, canActivate: [AuthGuard] },
  // {path: 'orderhistory', component:OrderhistoryComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  {path:"**", component: PageNotFoundComponent}
];

