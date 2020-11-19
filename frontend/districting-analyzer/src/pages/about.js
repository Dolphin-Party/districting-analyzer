import React, { useRef } from "react";
import L from "leaflet";
import {
  LayersControl,
  Map,
  TileLayer,
  LayerGroup,
  Marker
} from "react-leaflet";
const { Overlay } = LayersControl;

const center = [51, 0];

const icon = L.icon({
  iconSize: [25, 41],
  iconAnchor: [10, 41],
  popupAnchor: [2, -40],
  iconUrl: "https://unpkg.com/leaflet@1.6/dist/images/marker-icon.png",
  shadowUrl: "https://unpkg.com/leaflet@1.6/dist/images/marker-shadow.png"
});

export default function App() {
  const mapRef = useRef();
  const firstOverlayRef = useRef();
  const secondOverlayRef = useRef();

  const addLayers = () => {
    if (mapRef.current && firstOverlayRef.current) {
      const map = mapRef.current.leafletElement;
      const firstLayer = firstOverlayRef.current.leafletElement;
      const secondLayer = secondOverlayRef.current.leafletElement;
      [firstLayer, secondLayer].forEach(layer => map.addLayer(layer));
    }
  };

  const removeLayers = () => {
    if (mapRef.current && firstOverlayRef.current) {
      const map = mapRef.current.leafletElement;
      const firstLayer = firstOverlayRef.current.leafletElement;
      const secondLayer = secondOverlayRef.current.leafletElement;
      [firstLayer, secondLayer].forEach(layer => map.removeLayer(layer));
    }
  };

  return (
    <>
      <Map center={center} zoom={10} style={{ height: "90vh" }} ref={mapRef}>
        <LayersControl position="topright">
          <TileLayer
            attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            id="tl1"
          />

          <Overlay name="Layer 1">
            <LayerGroup id="lg1" ref={firstOverlayRef}>
              <Marker position={[51, 0.1]} icon={icon} />
            </LayerGroup>
          </Overlay>

          <Overlay name="Layer 2">
            <LayerGroup ref={secondOverlayRef}>
              <Marker position={[51, 0.2]} icon={icon} />
            </LayerGroup>
          </Overlay>
        </LayersControl>
      </Map>
      <button onClick={addLayers}>Add Layers</button>
      <button onClick={removeLayers}>Remove layers</button>
    </>
  );
}
