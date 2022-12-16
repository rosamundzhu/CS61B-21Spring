# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: I place the first hexagon in the middle of the x line where y = 0. Then each time moves the start point to left neighbour's leftest side and up n units.
If its position is smaller than 0, then move right for 2 * n for the next one. Until it meets the # required for the tessSize.

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: 

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: 

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: 
