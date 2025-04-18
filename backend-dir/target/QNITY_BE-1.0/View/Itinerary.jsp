<%-- 
    Document   : Intinerary.jsp
    Created on : Mar 1, 2025, 9:51:52 AM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Itinerary</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="./Home.jsp">QNITY</a>
            </div>
        </nav>
        <div class="container mt-5">

            <h2 class="text-center mb-4">Your Travel Itinerary To Quy Nhon</h2>

            <div class="card mb-3">
                <div class="card-header bg-primary text-white">Day 1: Arrival & City Tour</div>
                <div class="card-body">
                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>Time</th>
                                <th>Activity</th>
                                <th>Cost</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Morning</td>
                                <td>Arrive at Quy Nhon, check-in at hotel</td>
                                <td>$30-$50/night</td>
                            </tr>
                            <tr>
                                <td>Afternoon</td>
                                <td>Visit Thap Doi Cham Towers</td>
                                <td>$2</td>
                            </tr>
                            <tr>
                                <td>Afternoon</td>
                                <td>Enjoy local cuisine at Surf Bar</td>
                                <td>$10 per meal</td>
                            </tr>
                            <tr>
                                <td>Evening</td>
                                <td>Explore Quy Nhon Night Market</td>
                                <td>$5-$10</td>
                            </tr>
                        </tbody>
                    </table>
                    <p><strong>Total estimated cost for the day:</strong> $50 - $80</p>
                </div>
            </div>

            <div class="card mb-3">
                <div class="card-header bg-success text-white">Day 2: Adventure & Sightseeing</div>
                <div class="card-body">
                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>Time</th>
                                <th>Activity</th>
                                <th>Cost</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Morning</td>
                                <td>Hiking at Ky Co Beach (Boat tour)</td>
                                <td>$15/person</td>
                            </tr>
                            <tr>
                                <td>Afternoon</td>
                                <td>Visit Eo Gio - the windy pass</td>
                                <td>$3</td>
                            </tr>
                            <tr>
                                <td>Evening</td>
                                <td>Enjoy fresh seafood at Hoang Thao Restaurant</td>
                                <td>$15 per meal</td>
                            </tr>
                        </tbody>
                    </table>
                    <p><strong>Total estimated cost for the day:</strong> $35 - $40</p>
                </div>
            </div>

            <div class="card mb-3">
                <div class="card-header bg-warning text-white">Day 3: Relaxation & Departure</div>
                <div class="card-body">
                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>Time</th>
                                <th>Activity</th>
                                <th>Cost</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Morning</td>
                                <td>Spa & wellness retreat at FLC Quy Nhon</td>
                                <td>$20 for massage</td>
                            </tr>
                            <tr>
                                <td>Afternoon</td>
                                <td>Last-minute shopping at Big C Quy Nhon</td>
                                <td>$10-$20</td>
                            </tr>
                            <tr>
                                <td>Evening</td>
                                <td>Flight back home (Taxi to airport)</td>
                                <td>$10</td>
                            </tr>
                        </tbody>
                    </table>
                    <p><strong>Total estimated cost for the day:</strong> $40 - $50</p>
                </div>
            </div>
            <div class="justify-content-center d-flex gap-2">
                <button type="submit" class="btn btn-warning w-10 ">Save</button>
                <a href="Home.jsp"><button type="submit" class="btn btn-success w-10 " >Back to home</button></a>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
