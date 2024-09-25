package pt.isel.ls.router;

import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.command.CommandPath;
import pt.isel.ls.handlers.CommandHandler;

import java.util.ArrayList;
import java.util.List;

public class RouterTree {

    //Root of the Tree from where every path comes from
    private Node root = new Node(null, null);

    /**
     * Class Representing the Nodes in the Tree
     */
    static class Node {
        CommandHandler handler;
        String path;

        //List of Children Nodes that the Current Path Can Follow
        List<Node> children = new ArrayList<Node>();


        /**
         * Creates a Node
         *
         * @param handler handler assigned to the Node
         * @param path    String Template that represents the Path to reach this node
         */
        Node(CommandHandler handler, String path) {
            this.handler = handler;
            this.path = path;
        }

        // Adds a Children to the Current Children List
        void addChildren(Node n) {
            children.add(n);
        }

        void setHandler(CommandHandler handler) {
            this.handler = handler;
        }
    }

    /**
     * Adds a new Node to the Tree
     *
     * @param handler Handler appointed to the Node
     * @param upath   Path Template needed to Reach this Node
     */
    public void add(CommandHandler handler, String upath) {
        String[] path = new CommandPath(upath).getPath();

        Node curr = root;

        int childIdx;
        int pathIdx;

        //Creates the Given Path to the Node
        for (pathIdx = 0; pathIdx < path.length; pathIdx++) {
            childIdx = 0;
            while (true) {
                if (hasChildren(curr, childIdx)) {
                    if (curr.children.get(childIdx).path.equals(path[pathIdx])) {
                        curr = curr.children.get(childIdx);
                        break;
                    } else {
                        childIdx++;
                    }
                } else {
                    if (pathIdx < path.length) {
                        curr.addChildren(new Node(null, path[pathIdx]));
                        curr = curr.children.get(childIdx);
                        break;
                    }
                }
            }
        }

        curr.setHandler(handler);
    }

    private boolean hasChildren(Node n, int i) {
        return n.children.size() > i;
    }


    /**
     * Method to find the Node relating to the given Path and Return it's Handler
     *
     * @param method Given Method
     * @param path   Given Path
     * @return Handler that relates to the Path received
     * @throws MyInvalidPathException Exception that signalises the detection of an invalid Path
     */
    public CommandHandler find(String method, CommandPath path) throws MyInvalidPathException {
        int childIdx = 0;
        Node curr = root;

        //Finds Method Children
        while (hasChildren(curr, childIdx)) {
            if (curr.children.get(childIdx).path.equals(method)) {
                curr = curr.children.get(childIdx);
                childIdx = 0;
                break;
            }
            childIdx++;
        }

        //Finds the Node Relating to the Path
        boolean found = false;
        if (path != null) {
            String[] p = path.getPath();
            String s;
            for (int i = 1; i < p.length; i++) {
                found = false;
                s = p[i];
                if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
                    s = "1";
                }
                while (hasChildren(curr, childIdx)) {
                    if (curr.children.get(childIdx).path.equals(s)) {
                        curr = curr.children.get(childIdx);
                        childIdx = 0;
                        found = true;
                        break;
                    }
                    childIdx++;
                }
                if (!found) {
                    throw new MyInvalidPathException();
                }
            }
        }

        return curr.handler;
    }
}
