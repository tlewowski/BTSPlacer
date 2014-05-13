package views.map;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import calculations.SubscriberCenter;
import processing.core.PApplet;
import calculations.BTS;
import calculations.BtsType;
import calculations.Terrain;

import com.google.common.collect.Lists;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.providers.AbstractMapTileUrlProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class MapApplet extends PApplet {
	private final Dimension size;
    private final MarkerManager<Marker> markerManager;
	private UnfoldingMap map;
	private ArrayList<AbstractMapTileUrlProvider> providers;
	private JPopupMenu popupMenu;

	public MapApplet(Dimension dimension) {
		super();
		this.size = dimension;
        markerManager = new MarkerManager<Marker>();
		init();
	}

	private JMenuItem createMapProviderItem(String name, final int i) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				map.mapDisplay.setProvider(providers.get(i));
				map.draw();
			}
		});
		return menuItem;
	}

	@Override
	public void setup() {
		size(size.width, size.height);

		providers = Lists.newArrayList(new Google.GoogleMapProvider(),
				new Microsoft.AerialProvider(), new OpenStreetMap.OSMGrayProvider(),
				new OpenStreetMap.OpenStreetMapProvider());
		popupMenu = new JPopupMenu();
		popupMenu.add(createMapProviderItem("Google", 0));
		popupMenu.add(createMapProviderItem("OpenStreetMaps", 2));

		map = new UnfoldingMap(this, providers.get(0));

		// Show map around the location in the given zoom level.
		map.zoomAndPanTo(new Location(51.111, 17.030), 10);

		// Add mouse and keyboard interactions
		MapUtils.createDefaultEventDispatcher(this, map);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

        map.addMarkerManager(markerManager);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
	}

	@Override
	public void draw() {
		try {
			map.draw();
		} catch (ConcurrentModificationException e) {
			// FIXME bad idea for solution, but works
			// No it doesn't, it just suppresses the error
            // how about some synchronization here?
			System.out.println("Fast changing spinner value made this exception.");
			System.out.println("Give some rest !");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// do nothing
			}
			map.draw();
		}
	}

    public void addBtsMarker(Location location) {
        markerManager.addMarker(new BTS(location, BtsType.CIRCULAR));
    }

    public void addSubscriberCenterMarker(Location location) {
        markerManager.addMarker(new SubscriberCenter(1000.0, location, 0.5, 0.5));
    }

	public void addBtsMarker(Location location, BtsType type) {
        markerManager.addMarker(new BTS(location, type));
	}

	public void resetTerrain(Terrain terrain) {
        markerManager.clearMarkers();
        for (BTS bts : terrain.getBtss()) {
            markerManager.addMarker(bts);
        }
        for (SubscriberCenter sc : terrain.getSubscriberCenters()) {
            markerManager.addMarker(sc);
        }
	}

}