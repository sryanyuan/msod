function enter(pi) {
	pi.getPlayer().saveLocation(Packages.net.sf.odinms.server.maps.SavedLocationType.FREE_MARKET);
	pi.warp(910000000, "st00");
	return true;
}
