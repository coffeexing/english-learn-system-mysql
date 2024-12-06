package cn.example.demo.common.tools.tree;

import cn.example.demo.common.model.tree.SimpleTreeNode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 树形结构工具类
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-25 16:23
 */
public class TreeUtils {
    /**
     * 默认根节点ID
     */
    public static final Integer ROOT_NODE = 0;

    /**
     * <p>
     * 平铺列表结构 -> 树结构（递归实现）
     * </P>
     *
     * @param nodeList
     * @param node
     * @return
     */
    public static List<SimpleTreeNode> listConvertTree(List<SimpleTreeNode> nodeList, Integer node) {
        List<SimpleTreeNode> treeNodeList = new ArrayList<>();  // 存放已处理节点的数组
        nodeList.forEach(n -> {
            if (n.getParentNode().equals(node)) {   // 当前元素父节点等于传入的节点值
                // 递归执行源节点列表，并把当前元素节点作为参数，寻找该元素下所有子节点
                n.setChildren(listConvertTree(nodeList, n.getNode()));
                // 将自身保存到已处理列表
                treeNodeList.add(n);
            }
        });
        return treeNodeList;
    }

    /**
     * <p>
     * 平铺列表结构 -> 树结构（非递归实现）
     * </P>
     *
     * @param nodeList
     * @param rootNode
     * @return
     */
    public static List<SimpleTreeNode> listConvertToTree(List<SimpleTreeNode> nodeList, Integer rootNode) {
        List<SimpleTreeNode> treeNodeList = new ArrayList<>();  // 存放已处理节点的数组
        nodeList.forEach(n -> {
            n.setChildren(new ArrayList<>());
            if (n.getParentNode().equals(rootNode)) {   // 若当前元素父节点为根节点，直接保存到已处理列表
                treeNodeList.add(n);
            } else {
                // 找到当前元素的父节点元素，若存在，则将其添加到父节点元素的 children 字段下
                nodeList.stream().anyMatch(o -> {
                    if (n.getParentNode().equals(o.getNode())) {
                        if (o.getChildren() == null) {
                            o.setChildren(new ArrayList<>());
                        }
                        o.getChildren().add(n);
                        return true;
                    }
                    return false;
                });
            }
        });
        return treeNodeList;
    }

    /**
     * <p>
     * 深度优先遍历：树结构 -> 平铺列表（递归实现）
     * </P>
     *
     * @param treeNodeList
     * @param nodeList
     * @return
     */
    public static List<SimpleTreeNode> deepErgodicTree(List<SimpleTreeNode> treeNodeList, List<SimpleTreeNode> nodeList) {
        if (treeNodeList != null && !treeNodeList.isEmpty()) {
            for (SimpleTreeNode tn : treeNodeList) {
                nodeList.add(tn);
                // 列出该节点下的所有子节点
                if (tn.getChildren() != null && !tn.getChildren().isEmpty()) {
                    deepErgodicTree(tn.getChildren(), nodeList);
                }
                // children 设为空
                tn.setChildren(null);
            }
        }

        return nodeList;
    }

    /**
     * <p>
     * 【深度优先遍历】：递归实现
     * </P>
     *
     * @param file
     */
    public static int recurseFiles(File file) {
        System.out.println(file);

        int i = 0;
        i++;

        // 列出该节点下的所有子节点
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                // 递归调用
                i += recurseFiles(f);
            }
        }

        return i;
    }

    /**
     * <p>
     * 【深度优先遍历】：堆栈实现，后进先出
     * </P>
     *
     * @param file
     */
    public static void stackRecurseFiles(File file) {
        LinkedList<File> linkedList = new LinkedList<>();
        linkedList.add(file);
        int i = 0;
        int j = 0;

        while (!linkedList.isEmpty()) {
            // 出栈
            File f = linkedList.poll();
            // 列出该节点下的所有子节点
            File[] files = f.listFiles();
            if (files != null) {
                // 倒叙输出，确保树结构正序遍历
                for (int k = files.length - 1; k > -1; k--) {
                    // 压栈
                    linkedList.push(files[k]);

                }
            }

            if (!f.isDirectory()) {
                j++;
            }
            i++;
            System.out.println(f);
        }

        System.out.println("总文件数量(含目录）：" + i);
        System.out.println("总文件数量：" + j);
    }

    /**
     * <p>
     * 【广度优先遍历】：队列实现，先进先出
     * </P>
     *
     * @param file
     */
    public static void queueRecurseFiles(File file) {
        LinkedList<File> linkedList = new LinkedList<>();
        linkedList.add(file);
        int i = 0;
        int j = 0;

        while (!linkedList.isEmpty()) {
            // 出栈
            File f = linkedList.poll();
            // 列出该节点下的所有子节点
            File[] files = f.listFiles();
            if (files != null) {
                // 追加所有子节点到队列
                linkedList.addAll(Arrays.asList(files));
            }

            if (!f.isDirectory()) {
                j++;
            }
            i++;
            System.out.println(f);
        }

        System.out.println("总文件数量(含目录）：" + i);
        System.out.println("总文件数量：" + j);
    }
}
