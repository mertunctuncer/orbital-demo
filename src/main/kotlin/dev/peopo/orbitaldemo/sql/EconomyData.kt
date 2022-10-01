package dev.peopo.orbitaldemo.sql

import dev.peopo.orbitaldemo.util.config
import dev.peopo.skuerrel.annotation.Column
import dev.peopo.skuerrel.annotation.PrimaryKey
import dev.peopo.skuerrel.annotation.Table

@Table("player_balances")
class EconomyData {
	@Column(0, 36) @PrimaryKey
	var uuid: String = "null"
	@Column(1 )
	var balance: Double = config.getDouble("initial_balance")
}