package com.example.navegaciontest.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.navegaciontest.R
import com.example.navegaciontest.UserViewModel
import com.example.navegaciontest.databinding.FragmentProfileBinding
import com.example.navegaciontest.ui.login.LoginFragment


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val userViewModel: UserViewModel by activityViewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //SE OBTIENE EL NAVCONTROLLER
        navController = findNavController()

        //SE OBTIENE EL BUNDLE DEL FRAGMENT ACTUAL
        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle

        //SIEMPRE SE ESTA ESCUCHANDO EL BUNDLE DE ESTA PANTALLA
        savedStateHandle.getLiveData<Boolean>(LoginFragment.LOGIN_SUCCESSFUL)
            .observe(
                currentBackStackEntry,
                Observer { success ->

                    //SI LA RESPUESTA ES FALSE
                    if (!success) {
                        //SE OBTIENE EL START DESTINATION
                        val startDestination = navController.graph.startDestinationId

                        //CREAMOS OPCIONES
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(startDestination, true)
                            .build()

                        //LO DIRECIONAMOS AL FRAGMENTO INICIAL
                        navController.navigate(startDestination, null, navOptions)

                    }

                }
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //SE OBSERVA EL ESTADO SI EL USUARIO ESTA LOGUEADO
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user) {
                //SE MUESTRA QUE ES BIENVENIDO
                showWelcomeMessage()
            } else {
                //SE NAVEGA HACIA EL LOGIN
                navController.navigate(R.id.loginFragment)
            }
        }

    }

    private fun showWelcomeMessage() {
        Toast.makeText(requireContext(), "BIENVENIDO", Toast.LENGTH_LONG).show()
    }


}