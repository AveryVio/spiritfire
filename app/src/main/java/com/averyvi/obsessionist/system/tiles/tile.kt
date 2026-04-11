package com.averyvi.obsessionist.system.tiles

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

data class StateModel(val enabled: Boolean, val label: String, val icon: Icon)

class MyQSTileService: TileService() {

    // Called when the user adds your tile.
    override fun onTileAdded() {
        super.onTileAdded()
    }
    // Called when your app can update your tile.
    override fun onStartListening() {
        super.onStartListening()
    }

    // Called when your app can no longer update your tile.
    override fun onStopListening() {
        super.onStopListening()
    }
    var counter = 0
    // Called when the user taps on your tile in an active or inactive state.
    override fun onClick() {
        super.onClick()
        counter++
        qsTile.state = if (counter % 2 == 0) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.label = "Clicked $counter times"
        qsTile.contentDescription = qsTile.label
        qsTile.updateTile()
    }
    // Called when the user removes your tile.
    override fun onTileRemoved() {
        super.onTileRemoved()
    }
}