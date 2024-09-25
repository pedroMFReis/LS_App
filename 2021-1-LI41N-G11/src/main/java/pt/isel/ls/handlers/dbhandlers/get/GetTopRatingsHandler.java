package pt.isel.ls.handlers.dbhandlers.get;

import pt.isel.ls.MovieReviewApp;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.model.Ratings;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.get.GetTopRatingResult;

import java.sql.Connection;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

//GET /tops/ratings n=2&average=highest&min=2

/**
 * GET /tops/ratings - returns a list with the movies, given the following parameters:
 * <p>
 * n - max number of movies to list;
 * average - two possible values:
 * highest- movies with the highest average ratings
 * lowest- movies with the lowest average ratings
 * min - minimum number of votes (tatal do filme)
 * <p>
 * GET /tops/ratings n=2&average=highest&min=2
 */

public class GetTopRatingsHandler extends DbHandler {

    public GetTopRatingsHandler() {
        super("GET/tops/ratings");
    }


    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        //checking there are enough parameter
        if (p.size() != 3) {
            return false;
        }
        //checking if there is the correct parameters
        if (p.containsKey("n") && p.containsKey("average") && p.containsKey("min")) {
            return true;
        }
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws MyInvalidParametersException {

        HashMap<String, String> p = cmdReq.getParameters().getProprieties();
        if (!checkParams(p)) {
            throw new MyInvalidParametersException();
        }

        int maxMovies = Integer.parseInt(p.get("n"));
        String average = p.get("average");
        int minVotes = Integer.parseInt(p.get("min"));

        PriorityQueue<Ratings> priorityQueue = null;

        if (average.equals("lowest")) {
            priorityQueue = getSortedByLowestAverage(maxMovies, minVotes);

        } else if (average.equals("highest")) {
            priorityQueue = getSortedByHighestAverage(maxMovies, minVotes);
        }

        return new GetTopRatingResult(priorityQueue, maxMovies);
    }

    protected PriorityQueue<Ratings> getSortedByLowestAverage(int n, int minVotes) {

        PriorityQueue<Ratings> queue = new PriorityQueue(n, (o1, o2) -> {
            if (((Ratings) o1).getAverage() > ((Ratings) o2).getAverage()) {
                return 1;
            } else if (((Ratings) o1).getAverage() < ((Ratings) o2).getAverage()) {
                return -1;
            }
            return 0;
        });
        return insertInPriorityQueue(queue, minVotes);
    }

    protected PriorityQueue<Ratings> getSortedByHighestAverage(int n, int minVotes) {

        PriorityQueue<Ratings> queue = new PriorityQueue(n, (o1, o2) -> {
            if (((Ratings) o1).getAverage() < ((Ratings) o2).getAverage()) {
                return 1;
            } else if (((Ratings) o1).getAverage() > ((Ratings) o2).getAverage()) {
                return -1;
            }
            return 0;
        });
        return insertInPriorityQueue(queue, minVotes);
    }

    @Override
    public String getCommandDescription() {
        return "GET /tops/ratings:\n\t Returns the Top Ratings, Must be Given the Following Parameters:\n"
                + "\t\t n - max number of movies to list;\n"
                + "\t\t average - two possible values:\n"
                + "\t\t\t highest- movies with the highest average ratings\n"
                + "\t\t\t lowest- movies with the lowest average ratings\n"
                + "\t\t min - minimum number of votes;";
    }

    private PriorityQueue<Ratings> insertInPriorityQueue(PriorityQueue<Ratings> queue, int minVotes) {
        HashMap<Integer, Ratings> hashMapRatings = MovieReviewApp.getRatings();
        Set<Integer> keys = hashMapRatings.keySet();
        for (Integer key : keys) {
            if (minVotes <= hashMapRatings.get(key).getCounterVotes()) {
                queue.add(hashMapRatings.get(key));
            }
        }
        return queue;
    }
}