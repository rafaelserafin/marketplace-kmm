package com.rsddm.marketplace.features.shopping.icon

import com.rsddm.marketplace.core.BaseActionBundle


class ShoppingCartIcon {
    sealed class UIState (open val count: Int) {
        class Badge(count: Int) : UIState(count)
    }

    interface ActionBundle : BaseActionBundle {
        fun onShoppingCartClick()
    }
}

