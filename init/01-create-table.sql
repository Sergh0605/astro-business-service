CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE public.timezone_polygons
(
    tzid TEXT PRIMARY KEY,
    geom geometry(MultiPolygon, 4326) NOT NULL
);

CREATE INDEX idx_timezone_polygons_geom
    ON public.timezone_polygons
        USING GIST (geom);