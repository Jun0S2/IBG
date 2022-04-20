import React, { useState, useEffect } from "react";
import Map from "./component/Map";
import { Container } from "@mui/material";
import TitleToolbar from "../../component/TitleToolbar";

export default function Index() {
  const [long, setLong] = useState(0);
  const [lat, setLat] = useState(0);
  //const [loading, setLoading] = useState(false);
  const [addr, setAddr] = useState("");
  const { kakao } = window;

  useEffect(() => {
    //setLoading(true);
    navigator.geolocation.getCurrentPosition(function (pos) {
      var latitude = pos.coords.latitude;
      var longitude = pos.coords.longitude;
      setLong(longitude);
      setLat(latitude);
      getAddr();
      //console.log("address : " + addr);
    });
    //좌표  => 주소
    function getAddr() {
      let geocoder = new kakao.maps.services.Geocoder();
      let coord = new kakao.maps.LatLng(lat, long);

      let callback = function (result: any, status: number) {
        if (status === kakao.maps.services.Status.OK) {
          setAddr(result[0].address.region_3depth_name);
        }
      };
      geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
    }
    // setLoading(false);
  });

  return (
    <>
      <TitleToolbar title="주변 보드게임 카페 찾기" />
      <Container style={{ marginTop: 10, padding: 20 }}>
        <Map long={long} lat={lat} addr={addr} />
      </Container>
    </>
  );
}
