Automated help service reads prompt and messages from a file. In the Automated Help Service folder, sample service text file present. If you want to create your own service follow the following rules and text structure. Further information about code is provided as javadoc in source codes.

Rule 1: No empty line between lines.
Rule 2: Tree constructed as Breath First Search structure, meanig each node in this level must be constructed before next level.
Rule 3: In order to specify how many children this node has, type parent node's label and number of children seperated by space.
Rule 4: Every Node can have up to 3 children nodes.

********************** Text Structure Starts **********************
Node_1
Root_prompt
Root_message
Node_1 2  // Means Root node has 2 children
Node_1.1
Left_Node_Promt
Left_Node_Message
Node_1.2
Right_Node_Prompt
Right_Node_Message
********************** Text Structure Ends **********************

