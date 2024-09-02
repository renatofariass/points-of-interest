package com.interest.points.controller;

import com.interest.points.services.PointServices;
import com.interest.points.vos.point.PointVORequest;
import com.interest.points.vos.point.PointVOResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/points")
@Tag(name = "Points Of Interest", description = "Endpoints for searching and creating points")
public class PointController {
    @Autowired
    private PointServices pointServices;

    @Operation(summary = "Finds all Points", description = "Finds all Points",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PointVOResponse.class)))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            }
    )
    @GetMapping("/findAll")
    public ResponseEntity<List<PointVOResponse>> findAllPoints() {
        return ResponseEntity.ok().body(pointServices.findAllPoints());
    }

    @Operation(summary = "Finds Points of Interest by Proximity and Category",
            description = "Finds Points of Interest by Proximity and Category",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PointVOResponse.class)))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            }
    )
    @GetMapping("/nearby")
    public ResponseEntity<List<PointVOResponse>> findNearbyPoints(
            @RequestParam(name = "x") int x,
            @RequestParam(name = "y") int y,
            @RequestParam(name = "distance", defaultValue = "1000") int distance,
            @RequestParam(name = "categories", required = false) List<String> categories,
            @RequestParam(name = "openingHours", required = false) boolean openingHours){

        if (openingHours) {
            return ResponseEntity.ok().body(pointServices.findNearbyPoints(categories, x, y, distance));
        }

        return ResponseEntity.ok().body(pointServices.findNearbyPointsWithoutTimeFilter(categories, x, y, distance));
    }

    @Operation(summary = "Creates a new Point of Interest", description = "Creates a new Point of Interest",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = {@Content(schema = @Schema(implementation = PointVOResponse.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            }
    )
    @PostMapping
    public ResponseEntity<PointVOResponse> createPoint(@Valid @RequestBody PointVORequest obj) {
        PointVOResponse poi = pointServices.createPoint(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(poi.getId()).toUri();
        return ResponseEntity.created(uri).body(poi);
    }
}
