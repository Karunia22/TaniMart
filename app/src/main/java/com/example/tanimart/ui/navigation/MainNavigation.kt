package com.example.tanimart.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.tanimart.data.auth.AccountRepository
import com.example.tanimart.data.checkout.CheckoutRepository
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.payment.PaymentRepository
import com.example.tanimart.data.product.Product
import com.example.tanimart.ui.account.AccountScreen
import com.example.tanimart.ui.account.state.AccountViewModel
import com.example.tanimart.ui.account.state.AccountViewModelFactory
import com.example.tanimart.ui.auth.login.LoginScreen
import com.example.tanimart.ui.auth.login.state.LoginViewModel
import com.example.tanimart.ui.auth.register.RegisterScreen
import com.example.tanimart.ui.auth.register.state.RegisterViewModel
import com.example.tanimart.ui.checkout.CheckoutScreen
import com.example.tanimart.ui.home.HomeScreen
import com.example.tanimart.ui.navigation.ui.TaniMartBottomNavigation
import com.example.tanimart.ui.product.ProductListScreen
import com.example.tanimart.ui.product.ui.ProductDetailScreen
import com.example.tanimart.ui.profile.ProfileScreen

import com.example.tanimart.ui.address.AddAddressScreen
import com.example.tanimart.ui.address.AddressListScreen
import com.example.tanimart.ui.address.satate.AddressViewModel
import com.example.tanimart.ui.address.ui.EditAddressScreen
import com.example.tanimart.ui.auth.login.data.LoginUiEvent
import com.example.tanimart.ui.auth.logout.state.LogoutState
import com.example.tanimart.ui.auth.logout.state.LogoutViewModel
import com.example.tanimart.ui.auth.register.data.RegisterUiEvent
import com.example.tanimart.ui.cart.CartScreen
import com.example.tanimart.ui.cart.data.CartItem
import com.example.tanimart.ui.cart.state.CartViewModel
import com.example.tanimart.ui.checkout.state.CheckoutViewModel
import com.example.tanimart.ui.checkout.state.CheckoutViewModelFactory
import com.example.tanimart.ui.home.data.QuickMenu
import com.example.tanimart.ui.order.OrderDetailScreen
import com.example.tanimart.ui.order.OrderScreen
import com.example.tanimart.ui.order.state.OrderDetailViewModel
import com.example.tanimart.ui.order.state.OrderViewModel
import com.example.tanimart.ui.payment.PaymentInstructionScreen
import com.example.tanimart.ui.payment.PaymentScreen
import com.example.tanimart.ui.payment.state.PaymentViewModel
import com.example.tanimart.ui.payment.state.PaymentViewModelFactory
import com.example.tanimart.ui.product.state.ProductViewModel
import com.example.tanimart.ui.product.state.SellerProductViewModel
import com.example.tanimart.ui.product.ui.ProductContent
import com.example.tanimart.ui.product.ui.ProductLoadContent
import com.example.tanimart.ui.profile.data.ProfileMenu
import com.example.tanimart.ui.profile.state.ProfileViewModel
import com.example.tanimart.ui.seller.SellerDashboardScreen
import com.example.tanimart.ui.seller.application.SellerRegisterScreen
import com.example.tanimart.ui.seller.data.SellerDashboardMenu
import com.example.tanimart.ui.seller.orders.SellerOrderDetailScreen
import com.example.tanimart.ui.seller.orders.SellerOrdersScreen
import com.example.tanimart.ui.seller.products.SellerProductFormScreen
import com.example.tanimart.ui.seller.products.SellerProductsScreen
import com.example.tanimart.ui.seller.products.components.MySellerProductItem
import com.example.tanimart.ui.seller.productsdetail.SellerProductDetailScreen
import com.example.tanimart.ui.seller.sales.SellerSalesScreen
import com.example.tanimart.ui.seller.stock.SellerStockScreen
import com.example.tanimart.ui.seller.store.SellerSettingsScreen
import com.example.tanimart.ui.seller.store.SellerStoreScreen


private fun isMainMenu(route: String?): Boolean {
    return route in setOf(
        "home",
        "products",
        "profile"
    )
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation(
    startDestination: String = "login",
    sessionViewModel: com.example.tanimart.ui.auth.session.state.SessionViewModel? = null
) {

    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()
    val addressViewModel: AddressViewModel = viewModel()
    val addressUiState by addressViewModel.uiState
        .collectAsStateWithLifecycle()

    val selectedAddress = addressUiState.selectedAddress
    LaunchedEffect(addressViewModel) {
        addressViewModel.loadAddresses()
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry?.destination?.route

    val loginViewModel: LoginViewModel = viewModel()

    val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    val logoutViewModel: LogoutViewModel = viewModel()

    val logoutState by logoutViewModel.state
        .collectAsStateWithLifecycle()

    val registerViewModel: RegisterViewModel = viewModel()
    val registerUiState by registerViewModel.uiState
        .collectAsStateWithLifecycle()

    val profileViewModel: ProfileViewModel = viewModel()
    val accountViewModel: AccountViewModel = viewModel(
        factory = AccountViewModelFactory(
            AccountRepository(RetrofitClient.accountApi)
        )
    )

    val checkoutViewModel: CheckoutViewModel = viewModel(
        factory = CheckoutViewModelFactory(
            CheckoutRepository(RetrofitClient.checkoutApi)
        )
    )

    val paymentRepository = PaymentRepository(
        RetrofitClient.paymentApi
    )

    val paymentViewModel: PaymentViewModel = viewModel(
        factory = PaymentViewModelFactory(paymentRepository)
    )

    val paymentUiState by paymentViewModel.uiState
        .collectAsStateWithLifecycle()

    val checkoutUiState by checkoutViewModel.uiState.collectAsState()
    val orderViewModel: OrderViewModel = viewModel()

    val showBottomBar = currentRoute in listOf(
        "home",
        "products",
        "profile"
    )


    val productViewModel: ProductViewModel = viewModel()

    val productUiState by productViewModel.uiState
        .collectAsStateWithLifecycle()

    val products = productUiState.products

    Scaffold(
        bottomBar = {

            if (showBottomBar) {

                TaniMartBottomNavigation(
                    currentRoute = currentRoute,

                    onItemClick = { item ->

                        navController.navigate(
                            item.route
                        ) {

                            popUpTo("home") {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }

    ) { innerPadding ->


        /*
         * ======================================================
         * NAVIGATION HOST
         * ======================================================
         */
        NavHost(
            navController = navController,

            startDestination = startDestination,

            modifier = Modifier.padding(
                innerPadding
            ),



            enterTransition = {
                val fromMainMenu = isMainMenu(
                    initialState.destination.route
                )
                val toMainMenu = isMainMenu(
                    targetState.destination.route
                )

                if (fromMainMenu && toMainMenu) {
                    fadeIn(animationSpec = tween(180))
                } else {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(220)
                    ) + fadeIn(animationSpec = tween(220))
                }
            },

            exitTransition = {
                val fromMainMenu = isMainMenu(
                    initialState.destination.route
                )
                val toMainMenu = isMainMenu(
                    targetState.destination.route
                )

                if (fromMainMenu && toMainMenu) {
                    fadeOut(animationSpec = tween(180))
                } else {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(220)
                    ) + fadeOut(animationSpec = tween(220))
                }
            },

            popEnterTransition = {
                val fromMainMenu = isMainMenu(
                    initialState.destination.route
                )
                val toMainMenu = isMainMenu(
                    targetState.destination.route
                )

                if (fromMainMenu && toMainMenu) {
                    fadeIn(animationSpec = tween(180))
                } else {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(220)
                    ) + fadeIn(animationSpec = tween(220))
                }
            },

            popExitTransition = {
                val fromMainMenu = isMainMenu(
                    initialState.destination.route
                )
                val toMainMenu = isMainMenu(
                    targetState.destination.route
                )

                if (fromMainMenu && toMainMenu) {
                    fadeOut(animationSpec = tween(180))
                } else {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(220)
                    ) + fadeOut(animationSpec = tween(220))
                }
            }


        ) {


            /*
             * ==================================================
             * HOME
             * ==================================================
             */
            composable("home") {
                ProductLoadContent(
                    isLoading = productUiState.isLoading,
                    errorMessage = productUiState.errorMessage,
                    onRetry = productViewModel::loadProducts
                ) {
                    HomeScreen(
                        products = products,
                        onProductClick = { productId ->
                            navController.navigate("product_detail/$productId")
                        },
                        onMenuClick = { menu ->
                            when (menu) {
                                QuickMenu.CART -> {
                                    navController.navigate("cart")
                                }

                                QuickMenu.ORDER -> {
                                    navController.navigate("orders")
                                }

                                QuickMenu.ADDRESS -> {
                                    navController.navigate("addresses")
                                }
                            }
                        }
                    )
                }
            }


            composable("products") {
                ProductLoadContent(
                    isLoading = productUiState.isLoading,
                    errorMessage = productUiState.errorMessage,
                    onRetry = productViewModel::loadProducts
                ) {
                    ProductListScreen(
                        products = products,
                        onProductClick = { productId ->
                            navController.navigate("product_detail/$productId")
                        }
                    )
                }
            }


            /*
             * ==================================================
             * PRODUCT DETAIL
             * ==================================================
             */
            composable(
                route = "product_detail/{productId}",
                arguments = listOf(
                    navArgument("productId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val productId =
                    backStackEntry.arguments?.getInt("productId")

                if (productId != null) {
                    ProductDetailScreen(
                        productId = productId,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onAddToCartClick = { product, quantity ->
                            cartViewModel.addToCart(
                                product = product,
                                quantity = quantity
                            )
                        },
                        onBuyNowClick = { product, quantity ->
                            navController.navigate(
                                "checkout/${product.id}/$quantity"
                            )
                        }
                    )
                }
            }


            composable(
                route = "checkout/{productId}/{quantity}",
                arguments = listOf(
                    navArgument("productId") {
                        type = NavType.IntType
                    },
                    navArgument("quantity") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val productId =
                    backStackEntry.arguments?.getInt("productId")

                val quantity =
                    backStackEntry.arguments?.getInt("quantity") ?: 1

                val product = products.find {
                    it.id == productId
                }

                ProductLoadContent(
                    isLoading = productUiState.isLoading,
                    errorMessage = productUiState.errorMessage,
                    onRetry = productViewModel::loadProducts
                ) {
                    if (product != null && quantity > 0) {
                        val checkoutItems = listOf(
                            CartItem(
                                product = product,
                                quantity = quantity,
                                selected = true
                            )
                        )


                        CheckoutScreen(
                            items = checkoutItems,
                            address = selectedAddress,
                            uiState = checkoutUiState,
                            onBackClick = {
                                navController.popBackStack()
                            },
                            onAddressClick = {
                                navController.navigate("addresses")
                            },
                            onPaymentClick = {
                                val addressId = selectedAddress?.id

                                if (
                                    productId != null &&
                                    addressId != null &&
                                    quantity > 0 &&
                                    !checkoutUiState.isLoading
                                ) {
                                    checkoutViewModel.buyNow(
                                        productId = productId,
                                        quantity = quantity,
                                        addressId = addressId
                                    )
                                } else if (addressId == null) {
                                    navController.navigate("addresses")
                                }
                            }
                        )
                    } else {
                        Text(
                            text = "Produk atau jumlah pembelian tidak valid."
                        )
                    }
                }

                LaunchedEffect(checkoutUiState.checkoutResponse) {
                    val order = checkoutUiState.checkoutResponse?.order

                    if (order != null) {
                        navController.navigate("payment/${order.id}") {
                            launchSingleTop = true
                        }

                        checkoutViewModel.clearResult()
                    }
                }

            }

            composable("profile") {
                val profileUiState by profileViewModel.uiState
                    .collectAsStateWithLifecycle()

                ProfileScreen(
                    uiState = profileUiState,
                    onMenuClick = { menu ->
                        when (menu) {

                            ProfileMenu.ACCOUNT -> {
                                navController.navigate("account")
                            }

                            ProfileMenu.ADDRESS -> {
                                navController.navigate("addresses")
                            }

                            ProfileMenu.ORDER -> {
                                navController.navigate("orders")
                            }
                            ProfileMenu.TOKO_SAYA ->{
                                navController.navigate("seller_dashboard")
                            }
                            ProfileMenu.DAFTAR_PENJUAL -> {
                                navController.navigate("seller_register")
                            }
                            ProfileMenu.LOGOUT -> {
                                logoutViewModel.logout()
                            }
                        }
                    },
                    onRetryClick = {
                        profileViewModel.refreshProfile()
                    }
                )

                LaunchedEffect(logoutState) {

                    if (logoutState is LogoutState.Success) {
                        sessionViewModel?.markLoggedOut()
                        logoutViewModel.reset()

                        navController.navigate("login") {

                            popUpTo("home") {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                }

            }

            composable("account") {
                AccountScreen(
                    viewModel = accountViewModel,
                    onBack = {
                        navController.popBackStack()
                    },
                    onChangePrimaryAddress = {
                        navController.navigate("account/addresses")
                    }
                )
            }

            composable("account/addresses") {
                AddressListScreen(
                    viewModel = addressViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAddressSelected = {
                        addressViewModel.loadAddresses()
                        accountViewModel.loadAccount()
                        navController.popBackStack()
                    },
                    onAddAddressClick = {
                        navController.navigate("account/add_address")
                    },

                    // Membuka halaman edit alamat yang sudah ada.
                    onEditAddressClick = { addressId ->
                        navController.navigate(
                            "account/edit_address/$addressId"
                        )
                    },
                    selectPrimaryMode = true
                )
            }

            composable("account/add_address") {
                AddAddressScreen(
                    viewModel = addressViewModel,

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onAddressAdded = {
                        // Muat ulang daftar alamat sebelum kembali.
                        addressViewModel.loadAddresses()

                        navController.popBackStack()
                    }
                )
            }

            composable("account/edit_address/{addressId}") { backStackEntry ->

                val addressId = backStackEntry.arguments
                    ?.getString("addressId")
                    ?.toIntOrNull()

                val addressUiState by addressViewModel.uiState
                    .collectAsStateWithLifecycle()

                val address = addressUiState.addresses
                    .find { it.id == addressId }

                if (address != null) {
                    EditAddressScreen(
                        address = address,
                        viewModel = addressViewModel,

                        onBackClick = {
                            navController.popBackStack()
                        },

                        onAddressUpdated = {
                            addressViewModel.loadAddresses()
                            navController.popBackStack()
                        }
                    )
                }
            }


            composable("login") {

                LoginScreen(
                    uiState = loginUiState,
                    uiEvent = loginViewModel.uiEvent,
                    onEmailChange = loginViewModel::onEmailChange,
                    onPasswordChange = loginViewModel::onPasswordChange,
                    onLoginClick = loginViewModel::login,
                    onRegisterClick = {
                        navController.navigate("register")
                    },
                    onForgotPasswordClick = {
                        navController.navigate("forgot_password")
                    },
                    onLoginSuccess = {
                        sessionViewModel?.markAuthenticated()
                        navController.navigate("home") {
                            popUpTo("login") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )


            }

            composable("register") {
                RegisterScreen(
                    uiState = registerUiState,
                    uiEvent = registerViewModel.uiEvent,

                    onNameChange = {
                        registerViewModel.onNameChange(it)
                    },

                    onEmailChange = {
                        registerViewModel.onEmailChange(it)
                    },

                    onPasswordChange = {
                        registerViewModel.onPasswordChange(it)
                    },

                    onConfirmPasswordChange = {
                        registerViewModel.onConfirmPasswordChange(it)
                    },

                    onRegisterClick = {
                        registerViewModel.register()
                    },

                    onLoginClick = {
                        navController.navigate("login") {
                            popUpTo("register") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },

                    onRegisterSuccess = {
                        sessionViewModel?.markAuthenticated()
                        navController.navigate("home") {
                            popUpTo("register") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("cart") {
                CartScreen(
                    viewModel = cartViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCheckoutClick = {
                        navController.navigate("checkout")
                    }
                )
            }


            composable("checkout") {
                val checkoutRepository = CheckoutRepository(
                    RetrofitClient.checkoutApi
                )

                val checkoutViewModel: CheckoutViewModel = viewModel(
                    factory = CheckoutViewModelFactory(checkoutRepository)
                )

                val uiState by checkoutViewModel.uiState.collectAsState()

                val selectedItems = cartViewModel.getSelectedItems()

                val cartItemIds = selectedItems.mapNotNull { it.id }

                val canCheckout =
                    selectedAddress != null &&
                            selectedItems.isNotEmpty() &&
                            cartItemIds.size == selectedItems.size &&
                            !uiState.isLoading

                CheckoutScreen(
                    items = selectedItems,
                    address = selectedAddress,
                    uiState = uiState,

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onAddressClick = {
                        navController.navigate("addresses")
                    },

                    onPaymentClick = {
                        val addressId = selectedAddress?.id

                        if (canCheckout && addressId != null) {
                            checkoutViewModel.checkout(
                                addressId = addressId,
                                cartItemIds = cartItemIds
                            )
                        }
                    }
                )

                LaunchedEffect(checkoutUiState.checkoutResponse) {
                    val order = checkoutUiState.checkoutResponse?.order

                    if (order != null) {
                        navController.navigate("payment/${order.id}") {
                            launchSingleTop = true
                        }

                        checkoutViewModel.clearResult()
                    }
                }

            }

            composable("addresses") {
                AddressListScreen(
                    viewModel = addressViewModel,

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onAddressSelected = {
                        addressViewModel.loadAddresses()
                        navController.popBackStack()
                    },

                    onAddAddressClick = {
                        navController.navigate("add_address")
                    },

                    onEditAddressClick = { addressId ->
                        navController.navigate(
                            "edit_address/$addressId"
                        )
                    }
                )
            }

            composable("add_address") {

                AddAddressScreen(
                    viewModel = addressViewModel,

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onAddressAdded = {
                        navController.popBackStack()
                    }
                )
            }

            composable("edit_address/{addressId}") { backStackEntry ->

                val addressId =
                    backStackEntry.arguments
                        ?.getString("addressId")
                        ?.toIntOrNull()

                val uiState by addressViewModel.uiState.collectAsStateWithLifecycle()

                val address =
                    uiState.addresses.find { it.id == addressId }

                if (address != null) {
                    EditAddressScreen(
                        address = address,
                        viewModel = addressViewModel,

                        onBackClick = {
                            navController.popBackStack()
                        },

                        onAddressUpdated = {
                            navController.popBackStack()
                        }
                    )
                }
            }


            composable(
                route = "payment-instruction/{orderId}",
                arguments = listOf(
                    navArgument("orderId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val orderId = backStackEntry.arguments?.getInt("orderId")
                    ?: return@composable


                val paymentResponse = paymentUiState.paymentResponse

                if (
                    paymentResponse != null &&
                    paymentUiState.orderId == orderId
                ) {
                    PaymentInstructionScreen(
                        orderId = orderId,
                        paymentResponse = paymentResponse,
                        viewModel = paymentViewModel,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onPaymentSuccess = {
                            navController.popBackStack(
                                route = "payment/$orderId",
                                inclusive = true
                            )
                        }
                    )
                } else {
                    Text("Instruksi pembayaran tidak tersedia.")
                }
            }


            composable(
                route = "payment/{orderId}",
                arguments = listOf(
                    navArgument("orderId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val orderId = backStackEntry.arguments?.getInt("orderId")
                    ?: return@composable

                PaymentScreen(
                    orderId = orderId,
                    viewModel = paymentViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onPaymentCreated = {
                        navController.navigate("payment-instruction/$orderId") {
                            launchSingleTop = true
                        }
                    }
                )
            }


            composable("orders") {
                val lifecycleOwner = LocalLifecycleOwner.current

                DisposableEffect(lifecycleOwner, orderViewModel) {
                    val observer = LifecycleEventObserver{ _, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            orderViewModel.refreshOrders()
                        }
                    }

                    lifecycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }

                OrderScreen(
                    viewModel = orderViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onOrderClick = { orderId ->
                        navController.navigate("order-detail/$orderId")
                    }
                )
            }


            composable("order-detail/{orderId}") { backStackEntry ->
                val orderId = backStackEntry.arguments
                    ?.getString("orderId")
                    ?.toIntOrNull()

                if (orderId != null) {
                    val detailViewModel: OrderDetailViewModel = viewModel()

                    OrderDetailScreen(
                        orderId = orderId,
                        viewModel = detailViewModel,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable("seller_register") {
                SellerRegisterScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onOpenDashboard = {
                        navController.navigate("seller_dashboard") {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("seller_dashboard") {
                val sellerProductViewModel: SellerProductViewModel = viewModel()

                SellerDashboardScreen(
                    viewModel = sellerProductViewModel,
                    onMenuClick = { menu ->
                        when (menu) {
                            SellerDashboardMenu.PRODUCTS -> {
                                navController.navigate("seller_products")
                            }

                            SellerDashboardMenu.ORDERS -> {
                                navController.navigate("seller_orders")
                            }

                            SellerDashboardMenu.SALES -> {
                                navController.navigate("seller_sales")
                            }

                            SellerDashboardMenu.STOCK -> {
                                navController.navigate("seller_stock")
                            }

                            SellerDashboardMenu.MY_STORE -> {
                                navController.navigate("seller_store")
                            }

                            SellerDashboardMenu.SETTINGS -> {
                                navController.navigate("seller_settings")
                            }
                        }
                    }
                )
            }

            composable("seller_products") {
                SellerProductsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAddProductClick = {
                        navController.navigate("seller_product_form")
                    },
                    onProductClick = { product ->
                        navController.navigate(
                            "seller_product_detail/${product.id}"
                        )
                    },
                    onEditProductClick = { product ->
                        navController.navigate(
                            "seller_edit_product/${product.id}"
                        )
                    }
                )
            }

            composable("seller_product_form") {
                SellerProductFormScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onProductCreated = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = "seller_product_detail/{productId}",
                arguments = listOf(
                    navArgument("productId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val productId = backStackEntry.arguments
                    ?.getInt("productId")

                if (productId != null) {
                    SellerProductDetailScreen(
                        productId = productId,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onEditClick = { product ->
                            navController.navigate(
                                "seller_edit_product/${product.id}"
                            )
                        }
                    )
                }
            }

            composable(
                route = "seller_edit_product/{productId}",
                arguments = listOf(
                    navArgument("productId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val productId = requireNotNull(
                    backStackEntry.arguments?.getInt("productId")
                )

                SellerProductFormScreen(
                    productId = productId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onProductCreated = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = "seller_orders") {
                SellerOrdersScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },

                    onOrderClick = { orderId ->
                        navController.navigate("seller_order_detail/$orderId")
                    },
                )
            }

            composable(
                route = "seller_order_detail/{orderId}",
                arguments = listOf(
                    navArgument("orderId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val orderId = backStackEntry.arguments?.getInt("orderId")
                    ?: return@composable

                SellerOrderDetailScreen(
                    orderId = orderId,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = "seller_sales") {
                SellerSalesScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = "seller_stock") {
                SellerStockScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = "seller_store") {
                SellerStoreScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("seller_settings")
                    }
                )
            }

            composable(route = "seller_settings") {
                SellerSettingsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}