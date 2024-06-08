<aside class="left-sidebar" data-sidebarbg="skin6">
    <!-- Sidebar scroll-->
    <div class="scroll-sidebar" data-sidebarbg="skin6">
        <!-- Sidebar navigation-->
        <nav class="sidebar-nav">
            <ul id="sidebarnav">
                <li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${classpath }/admin/home"
                        aria-expanded="false"><i data-feather="home" class="feather-icon"></i><span
                            class="hide-menu">Dashboard</span></a></li>
                <li class="list-divider"></li>
                <li class="nav-small-cap"><span class="hide-menu">System Administration</span></li>

                <li class="sidebar-item"> <a class="sidebar-link" href="${classpath }/admin/category/list"
                        aria-expanded="false"><i data-feather="tag" class="feather-icon"></i><span
                            class="hide-menu">Categories
                        </span></a>
                </li>
                
                <li class="sidebar-item">
                	<a class="sidebar-link sidebar-link" href="${classpath }/admin/product/list " aria-expanded="false">
                		<i data-feather="message-square" class="feather-icon"></i>
                		<span class="hide-menu">Products</span>
                	</a>
                </li>
                
                <li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${classpath }/admin/user/list"
                        aria-expanded="false"><i data-feather="calendar" class="feather-icon"></i><span
                            class="hide-menu">Users</span></a></li>
                            
<%--                 <li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${classpath }/admin/role/list" --%>
<!--                         aria-expanded="false"><i data-feather="calendar" class="feather-icon"></i><span -->
<!--                             class="hide-menu">Roles</span></a></li> -->
                
				<li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${classpath }/admin/order/list"
                        aria-expanded="false"><i data-feather="calendar" class="feather-icon"></i><span
                            class="hide-menu">Orders</span></a></li>
                            
<%--                 <li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${classpath }/addmin/contact/list" --%>
<!--                         aria-expanded="false"><i data-feather="calendar" class="feather-icon"></i><span -->
<!--                             class="hide-menu">Customer contact</span></a></li> -->
				
               
                <li class="list-divider"></li>
                <li class="nav-small-cap"><span class="hide-menu">Authentication</span></li>

                <li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${classpath}/login"
                        aria-expanded="false"><i data-feather="lock" class="feather-icon"></i><span
                            class="hide-menu">Login
                        </span></a>
                </li>
                <li class="sidebar-item"> <a class="sidebar-link sidebar-link"
                        href="${classpath}/signup" aria-expanded="false"><i data-feather="lock"
                            class="feather-icon"></i><span class="hide-menu">Register
                        </span></a>
                </li>

                <li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${classpath}/logout"
                        aria-expanded="false"><i data-feather="log-out" class="feather-icon"></i><span
                            class="hide-menu">Logout</span></a></li>
            </ul>
        </nav>
        <!-- End Sidebar navigation -->
    </div>
    <!-- End Sidebar scroll-->
</aside>