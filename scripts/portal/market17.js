/**
-- Krypto JavaScript ------------------------------------------------------------------------------
	Free Market Portal
-- By ---------------------------------------------------------------------------------------------
	Xterminator
-- Description ------------------------------------------------------------------------------------
	From Nautilus to Free Market
-- Version Info -----------------------------------------------------------------------------------
	1.0 - First Version by Xterminator
-- Additional Comments ----------------------------------------------------------------------------
	None
---------------------------------------------------------------------------------------------------
**/

function enter(pi) {
	pi.getPlayer().saveLocation("FREE_MARKET");
	pi.warp(910000000, "st00");
	return true;
}