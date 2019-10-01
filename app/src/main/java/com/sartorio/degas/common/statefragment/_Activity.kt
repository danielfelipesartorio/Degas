package com.sartorio.degas.common.statefragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sartorio.degas.R

fun AppCompatActivity.showFragmentWithAnimation(
    fragment: Fragment, tag: String,
    enter: Int, exit: Int, popEnter: Int, popExit: Int
) {

    val transaction = supportFragmentManager.beginTransaction()

    transaction.addToBackStack(tag)
    transaction.setCustomAnimations(enter, exit, popEnter, popExit)
    transaction.replace(R.id.fragment_container, fragment, tag)
    transaction.commit()
}

fun AppCompatActivity.showFragmentEnterFromLeftAnimation(fragment: Fragment, tag: String) {
    showFragmentWithAnimation(
        fragment,
        tag,
        R.anim.enter_from_left,
        R.anim.exit_to_right,
        R.anim.enter_from_right,
        R.anim.exit_to_left
    )
}

fun AppCompatActivity.showFragmentEnterFromRightAnimation(fragment: Fragment, tag: String) {
    showFragmentWithAnimation(
        fragment,
        tag,
        R.anim.enter_from_right,
        R.anim.exit_to_left,
        R.anim.enter_from_left,
        R.anim.exit_to_right
    )
}