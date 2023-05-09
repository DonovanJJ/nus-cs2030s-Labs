import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * A BusSg class encapsulate the data related to the bus services and
 * bus stops in Singapore, and supports queries to the data.
 *
 * @author: Ooi Wei Tsang
 * @version: CS2030S AY22/23 Semester 2, Lab 8
 */
class BusSg {

  /**
   * Given a bus stop and a name, find the bus services that serve between
   * the given stop and any bus stop with matching mame.
   * @param  stop The bus stop.  Assume to be not null.
   * @param  searchString The (partial) name of other bus stops, assume not null.
   * @return The (optional) bus routes between the stops.
   */
  public static CompletableFuture<BusRoutes> findBusServicesBetween(
      BusStop stop, String searchString) {
    return stop.getBusServices().thenApply(x -> {
      Map<BusService, CompletableFuture<Set<BusStop>>> validServices = x
          .stream()
          .collect(Collectors.toMap(
              service -> service,
              service -> service.findStopsWith(searchString)));
      return new BusRoutes(stop, searchString, validServices);
    }).exceptionally(e -> {
      System.err.println("Unable to complete query: " + e);
      return new BusRoutes(stop, searchString, Map.of());
    });
  }
}
